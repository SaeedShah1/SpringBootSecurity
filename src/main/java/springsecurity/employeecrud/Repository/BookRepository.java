package springsecurity.employeecrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springsecurity.employeecrud.Entity.BookEntity;


public interface BookRepository extends JpaRepository<BookEntity, Long> {

    BookEntity findByName(String name);

}
