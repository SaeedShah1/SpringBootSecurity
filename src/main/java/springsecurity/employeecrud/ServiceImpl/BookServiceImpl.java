package springsecurity.employeecrud.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springsecurity.employeecrud.Entity.BookEntity;
import springsecurity.employeecrud.Repository.BookRepository;
import springsecurity.employeecrud.Service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

   @Autowired
   private BookRepository repository;

    @Override
    public BookEntity create(BookEntity entity) {
        return repository.save(entity);
    }

    @Override
    public BookEntity update(BookEntity entity) {
        return repository.save(entity);
    }

    @Override
    public BookEntity delete(BookEntity entity) {
        entity.setStatus(false);
        return repository.save(entity);
    }

    @Override
    public BookEntity findById(Long id) {
        Optional<BookEntity> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<BookEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<BookEntity> findAllByFilterWithPaging(Specification<BookEntity> specification, Pageable pageable) {
        return repository.findAll(specification,pageable);
    }

    @Override
    public List<BookEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<BookEntity> findAllByAuthorEntityId(Long id) {
        return repository.findAllByAuthorEntityId(id);
    }


}
