package springsecurity.employeecrud.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import springsecurity.employeecrud.Entity.BookEntity;


import java.util.List;

public interface BookService {
    BookEntity create(BookEntity entity);
    BookEntity update(BookEntity entity);
    BookEntity delete(BookEntity entity);
    BookEntity findById(Long id);
    List<BookEntity> findAll();
    Page<BookEntity> findAllByFilterWithPaging(Specification<BookEntity> specification, Pageable pageable);
    List<BookEntity>  findByName(String name);
    List<BookEntity> findAllByAuthor(String name);
}
