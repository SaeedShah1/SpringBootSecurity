package springsecurity.employeecrud.RolePermissionModel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springsecurity.employeecrud.DTO.StatusDTO;
import springsecurity.employeecrud.RolePermissionModel.DTO.UserDTO;
import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;
import springsecurity.employeecrud.RolePermissionModel.Service.UserService;
import springsecurity.employeecrud.RolePermissionModel.Transformer.UserTransfomer;


import java.util.List;
/**
 *
 * @author Saeed Shah
 */


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/create")
    public ResponseEntity<StatusDTO> create(@ModelAttribute UserDTO userDTO) {
        try {

            UserEntity userEntity1 = userService.findByUserName(userDTO.getUserName());
            if(userEntity1!=null){
                return  new ResponseEntity(new StatusDTO(0, " UserName Already Exist"), HttpStatus.OK);

            }
            UserEntity userEntity = UserTransfomer.toEntity(userDTO);
            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userEntity.setStatus(true);
            userService.create(userEntity);


            return new ResponseEntity(new StatusDTO(1, " Added Successfully", UserTransfomer.toDTO(userEntity)), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception occurred! " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/update")
    public ResponseEntity<StatusDTO> update(@ModelAttribute UserDTO userDTO) {

        try {

            UserEntity userEntity = userService.findById(Long.parseLong(userDTO.getId()));
            if (userEntity == null) {
                return new ResponseEntity(new StatusDTO(0, "User not found"), HttpStatus.NOT_FOUND);
            }
            UserEntity userEntity2 = userService.findByUserName(userDTO.getUserName());
            if(userEntity2!=null){
                return  new ResponseEntity(new StatusDTO(0, " UserName Can Not be changed"), HttpStatus.OK);

            }

            UserEntity userEntity1 = UserTransfomer.toEntity(userDTO);
            if(userDTO.getPassword()!=null){
                userEntity1.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }else{
                userEntity1.setPassword(userEntity.getPassword());
            }
            userEntity1.setUserName(userEntity.getUserName());
            userEntity1.setStatus(true);
            userService.create(userEntity1);

            return new ResponseEntity(new StatusDTO(1, "Updated"), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/delete")
    public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {

        try {
            UserEntity userEntity = userService.findById(id);
            if (userEntity != null) {
                userService.delete(userEntity);
                return new ResponseEntity(new StatusDTO(1, "Deleted"), HttpStatus.OK);
            } else {
                return new ResponseEntity<StatusDTO>(new StatusDTO(0, " Details not found!"), HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/view/{id}")
    public ResponseEntity<StatusDTO> getById(@PathVariable Long id) {
        try {
            UserEntity userEntity = userService.findById(id);

            if (userEntity != null) {
                UserDTO employeeDTO = UserTransfomer.toDTO(userEntity);
                return new ResponseEntity(employeeDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity(" Details not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "/getAll")
    public List<UserDTO> getAll() {
        List<UserEntity> userEntities = userService.findAll();
        return UserTransfomer.getDTOs(userEntities);
    }



}
