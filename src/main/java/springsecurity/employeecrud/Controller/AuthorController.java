package springsecurity.employeecrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsecurity.employeecrud.DTO.AuthorDTO;
import springsecurity.employeecrud.DTO.PageDTO;
import springsecurity.employeecrud.DTO.StatusDTO;
import springsecurity.employeecrud.Entity.AuthorEntity;
import springsecurity.employeecrud.Entity.BookEntity;
import springsecurity.employeecrud.SearchFilters.AuthorFilter;
import springsecurity.employeecrud.Service.AuthorService;
import springsecurity.employeecrud.Service.BookService;
import springsecurity.employeecrud.Transformer.AuthorTransformer;
import springsecurity.employeecrud.Utils.AllUtils;
import springsecurity.employeecrud.Utils.PaginationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;


    @PostMapping(value = "/create")
    public ResponseEntity<StatusDTO> create(@RequestBody AuthorDTO authorDTO) {
        try {

            AuthorEntity authorEntity = AuthorTransformer.toEntity(authorDTO);
            authorEntity.setStatus(true);
            authorService.create(authorEntity);

            return new ResponseEntity(new StatusDTO(1, " Added Successfully", AuthorTransformer.toDTO(authorEntity)), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception occurred! " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/update")
    public ResponseEntity<StatusDTO> update(@RequestBody AuthorDTO authorDTO) {

        try {

            AuthorEntity authorEntity = authorService.findById(Long.parseLong(authorDTO.getId()));
            if (authorEntity == null) {
                return new ResponseEntity(new StatusDTO(0, "Author not found"), HttpStatus.NOT_FOUND);
            }
            AuthorEntity authorEntity1 = AuthorTransformer.toEntity(authorDTO);

            authorEntity1.setStatus(true);
            authorService.update(authorEntity1);

            return new ResponseEntity(new StatusDTO(1, "Updated"), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {

        try {
            AuthorEntity authorEntity = authorService.findById(id);
            if (authorEntity != null) {
                deleteChild(id);
                authorService.delete(authorEntity);
                return new ResponseEntity(new StatusDTO(1, "Deleted"), HttpStatus.OK);
            } else {
                return new ResponseEntity<StatusDTO>(new StatusDTO(0, " Details not found!"), HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/view/{id}")
    public ResponseEntity<StatusDTO> getById(@PathVariable Long id) {
        try {
            AuthorEntity authorEntity = authorService.findById(id);

            if (authorEntity != null) {
                AuthorDTO employeeDTO = AuthorTransformer.toDTO(authorEntity);
                return new ResponseEntity(employeeDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity(" Author not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "/getAll")
    public List<AuthorDTO> getAll() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        return AuthorTransformer.getDTOs(authorEntities);
    }

    @GetMapping(value = "/getByName")
    public AuthorDTO getAllByName(@PathVariable String name) {
        AuthorEntity authorEntity = authorService.findByAuthorName(name);
        if(authorEntity!=null){
            return AuthorTransformer.toDTO(authorEntity);
        }

        return null;
    }
    @PostMapping(value = "/views")
    public PageDTO getAll(@RequestBody PaginationUtil paginationUtil,AuthorFilter filter) {
        Map<String, String> params=new HashMap<>();
        params.put("page",paginationUtil.getCurrentPage().toString());
        params.put("itemsPerPage",paginationUtil.getItemsPerPage().toString());
        params.put("sortBy",paginationUtil.getSortBy());
        params.put("direction",paginationUtil.getDirection());
        Page<AuthorEntity> page = authorService.findAllByFilterWithPaging(filter, AllUtils.createPageRequest(params));
        return new PageDTO(AuthorTransformer.getDTOs(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @PostMapping("/truncate")
    public ResponseEntity<StatusDTO> truncate(){
        try{
            List<AuthorEntity> authorEntities = authorService.findAll();
            if(!authorEntities.isEmpty()){
                for (AuthorEntity authorEntity: authorEntities){
                    deleteChild(authorEntity.getId());
                    authorService.delete(authorEntity);
                }
                return new ResponseEntity<StatusDTO>(new StatusDTO(1, "Truncated Successfully "), HttpStatus.OK);
            }else {
                return new ResponseEntity<StatusDTO>(new StatusDTO(0,"Author not found!"), HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            return new ResponseEntity<StatusDTO>(new StatusDTO(0, "Exception occurred: "+ex.getMessage()), HttpStatus.OK);
        }
    }

    public void deleteChild(Long id) {
        //deleted child Fac Deductables
        List<BookEntity> bookEntities= bookService.findAllByAuthorEntityId(id);
        for (BookEntity bookEntity : bookEntities) {

            bookService.delete(bookEntity);

        }
    }


}
