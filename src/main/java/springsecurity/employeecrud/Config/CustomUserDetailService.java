package springsecurity.employeecrud.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;
import springsecurity.employeecrud.RolePermissionModel.Service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity userEntity = userService.findByUserName(userName);
        if (userEntity != null) {
            return new User(userEntity.getUserName(), userEntity.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("user not found!");

        }
    }
}
