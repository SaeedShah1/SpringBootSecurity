package springsecurity.employeecrud.RolePermissionModel.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthenticatedUserEntity {
    private Long id;
    private String name;

    private String userName;

    private String email;

    private String token;

    private Boolean status;
}
