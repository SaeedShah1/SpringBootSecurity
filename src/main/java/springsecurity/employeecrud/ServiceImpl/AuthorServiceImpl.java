package springsecurity.employeecrud.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springsecurity.employeecrud.Entity.AuthorEntity;
import springsecurity.employeecrud.Repository.AuthorRepository;
import springsecurity.employeecrud.Service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository repository;


    @Override
    public AuthorEntity create(AuthorEntity entity) {
        return repository.save(entity);
    }

    @Override
    public AuthorEntity update(AuthorEntity entity) {
        return repository.save(entity);
    }

    @Override
    public AuthorEntity delete(AuthorEntity entity) {
        entity.setStatus(false);
        return repository.save(entity);
    }

    @Override
    public AuthorEntity findById(Long id) {
        Optional<AuthorEntity> optional = repository.findById(id);
        if(optional.isPresent()){
            return  optional.get();
        }
        return null;
    }

    @Override
    public List<AuthorEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<AuthorEntity> findAllByFilterWithPaging(Specification<AuthorEntity> specification, Pageable pageable) {
        return repository.findAll(specification,pageable);
    }

    @Override
    public AuthorEntity findByAuthorName(String name) {
        return repository.findByAuthorName(name);
    }
}
