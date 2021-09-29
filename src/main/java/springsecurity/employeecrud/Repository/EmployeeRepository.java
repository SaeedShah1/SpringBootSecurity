package springsecurity.employeecrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springsecurity.employeecrud.Entity.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    List<EmployeeEntity> findAll();

    EmployeeEntity findByName(String name);
}
