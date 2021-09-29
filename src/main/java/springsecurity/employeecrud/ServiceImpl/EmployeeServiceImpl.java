package springsecurity.employeecrud.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsecurity.employeecrud.Entity.EmployeeEntity;
import springsecurity.employeecrud.Repository.EmployeeRepository;
import springsecurity.employeecrud.Service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public EmployeeEntity create(EmployeeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public EmployeeEntity update(EmployeeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public EmployeeEntity delete(EmployeeEntity entity) {
        entity.setStatus(false);
        repository.save(entity);
        return entity;
    }

    @Override
    public EmployeeEntity findById(Long id) {
        Optional<EmployeeEntity> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }

        return null;
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public EmployeeEntity findByName(String name) {
        return repository.findByName(name);
    }
}
