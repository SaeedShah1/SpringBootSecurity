package springsecurity.employeecrud.RolePermissionModel.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;
import springsecurity.employeecrud.RolePermissionModel.Repository.UserRepository;
import springsecurity.employeecrud.RolePermissionModel.Service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserEntity create(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity delete(UserEntity entity) {
        entity.setStatus(false);
        userRepository.save(entity);
        return entity;
    }

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }

        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findByUserName(String name) {
        return userRepository.findByUserName(name);
    }
}
