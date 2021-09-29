package springsecurity.employeecrud.RolePermissionModel.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String id;

    private String name;
    private String password;

    private String userName;

    private String email;

    private String status;
}
