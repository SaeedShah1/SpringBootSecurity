package springsecurity.employeecrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springsecurity.employeecrud.Config.CustomUserDetailService;
import springsecurity.employeecrud.DTO.StatusDTO;
import springsecurity.employeecrud.RolePermissionModel.Entity.AuthenticatedUserEntity;
import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;
import springsecurity.employeecrud.RolePermissionModel.Service.UserService;
import springsecurity.employeecrud.Security.JwtRequest;
import springsecurity.employeecrud.Security.JwtUtil;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Saeed Shah
 */
@RestController
@RequestMapping(path = "/Login")
public class LoginController {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @PostMapping(value = "/auth")
    public ResponseEntity<StatusDTO> auth(@ModelAttribute JwtRequest jwtRequest) throws Exception {

        try {
            UserEntity userEntity = userService.findByUserName(jwtRequest.getUsername());
            if(userEntity==null || !userEntity.getPassword().equals(jwtRequest.getPassword())){
                return new ResponseEntity(new StatusDTO(0, "User Not Found!" +
                        "  Incorrect username or password!"), HttpStatus.NOT_FOUND);
            }

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
            AuthenticatedUserEntity authenticatedUser = getAuthenticatedUser(userDetails,userEntity);

                return new ResponseEntity(authenticatedUser, HttpStatus.OK);


        }
        catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured!"), HttpStatus.NOT_FOUND);

        }

    }

    private AuthenticatedUserEntity getAuthenticatedUser(UserDetails userDetails,UserEntity userEntity){

        AuthenticatedUserEntity authenticatedUserEntity = new AuthenticatedUserEntity();
        Set<String> permissionList = new HashSet<>();
        if(userEntity.getId()!=null){
            authenticatedUserEntity.setId(userEntity.getId());

        }
        if(userEntity.getStatus()!=null){
            authenticatedUserEntity.setStatus(userEntity.getStatus());
        }

        if(userEntity.getUserName()!=null){
            authenticatedUserEntity.setUserName(userEntity.getUserName());
        }

        if(userEntity.getName()!=null){
            authenticatedUserEntity.setName(userEntity.getName());
        }

        if(userEntity.getEmail()!=null){
            authenticatedUserEntity.setEmail(userEntity.getEmail());
        }


        String token = this.jwtUtil.generateToken(userDetails,permissionList);
        System.out.println("Token generated : " + token);

        authenticatedUserEntity.setToken(token);

        return authenticatedUserEntity;

    }


}
