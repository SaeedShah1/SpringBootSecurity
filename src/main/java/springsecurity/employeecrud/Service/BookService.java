package springsecurity.employeecrud.Service;

import springsecurity.employeecrud.Entity.BookEntity;


import java.util.List;

public interface BookService {
    BookEntity create(BookEntity entity);
    BookEntity update(BookEntity entity);
    BookEntity delete(BookEntity entity);
    BookEntity findById(Long id);
    List<BookEntity> findAll();
    BookEntity findByName(String name);
}
