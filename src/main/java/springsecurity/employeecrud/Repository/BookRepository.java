package springsecurity.employeecrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import springsecurity.employeecrud.Entity.BookEntity;

import java.util.List;


public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

    List<BookEntity>  findByName(String name);
    List<BookEntity> findAllByAuthor(String name);
}
