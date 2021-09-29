package springsecurity.employeecrud.RolePermissionModel.Transformer;

import springsecurity.employeecrud.RolePermissionModel.DTO.UserDTO;
import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserTransfomer {

    public static UserEntity toEntity(UserDTO userDTO){

        UserEntity userEntity = new UserEntity();

        if(userDTO.getId()!=null){
            userEntity.setId(Long.parseLong(userDTO.getId()));
        }
        if(userDTO.getName()!=null){
            userEntity.setName(userDTO.getName());
        }
        if(userDTO.getEmail()!=null){
            userEntity.setEmail(userDTO.getEmail());
        }
        if(userDTO.getUserName()!=null){
            userEntity.setUserName(userDTO.getUserName());
        }

        if(userDTO.getPassword()!=null){
            userEntity.setPassword(userDTO.getPassword());
        }


        return  userEntity;
    }

    public static UserDTO toDTO(UserEntity userEntity1){

        UserDTO userDTO = new UserDTO();

        if(userEntity1.getId()!=null){
            userDTO.setId(userEntity1.getId().toString());
        }
        if(userEntity1.getName()!=null){
            userDTO.setName(userEntity1.getName());
        }
        if(userEntity1.getEmail()!=null){
            userDTO.setEmail(userEntity1.getEmail());
        }
        if(userEntity1.getUserName()!=null){
            userDTO.setUserName(userEntity1.getUserName());
        }

        if(userEntity1.getStatus()!=null){
            userDTO.setStatus(userEntity1.getStatus().toString());
        }

        return  userDTO;
    }

    public static List<UserDTO> getDTOs(List<UserEntity> employeeEntities) {
        List<UserDTO> userDTOS = new ArrayList<>();
        employeeEntities.forEach(userEntity -> {
            userDTOS.add(toDTO(userEntity));
        });
        return userDTOS;
    }

}
