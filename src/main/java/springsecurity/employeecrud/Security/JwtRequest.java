package springsecurity.employeecrud.Security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtRequest {

    String username;
    String password;


}
