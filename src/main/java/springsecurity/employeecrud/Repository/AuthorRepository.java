package springsecurity.employeecrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import springsecurity.employeecrud.Entity.AuthorEntity;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> , JpaSpecificationExecutor<AuthorEntity> {

    AuthorEntity findByAuthorName(String name);
    List<AuthorEntity> findAll();
}
