package springsecurity.employeecrud.RolePermissionModel.Service;

import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity create(UserEntity entity);
    UserEntity update(UserEntity entity);
    UserEntity delete(UserEntity entity);
    UserEntity findById(Long id);
    List<UserEntity> findAll();
    UserEntity findByUserName(String name);
}
