package springsecurity.employeecrud.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import springsecurity.employeecrud.Entity.AuthorEntity;


import java.util.List;

public interface AuthorService {
    AuthorEntity create(AuthorEntity entity);
    AuthorEntity update(AuthorEntity entity);
    AuthorEntity delete(AuthorEntity entity);
    AuthorEntity findById(Long id);
    List<AuthorEntity> findAll();
    Page<AuthorEntity> findAllByFilterWithPaging(Specification<AuthorEntity> specification, Pageable pageable);
    AuthorEntity findByAuthorName(String name);

}
