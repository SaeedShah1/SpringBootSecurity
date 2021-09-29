package springsecurity.employeecrud.Service;

import springsecurity.employeecrud.Entity.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

    EmployeeEntity create(EmployeeEntity entity);
    EmployeeEntity update(EmployeeEntity entity);
    EmployeeEntity delete(EmployeeEntity entity);
    EmployeeEntity findById(Long id);
    List<EmployeeEntity> findAll();
    EmployeeEntity findByName(String name);
}
