package springsecurity.employeecrud.RolePermissionModel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findAll();
    UserEntity findByUserName(String userName);
}
