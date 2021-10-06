package springsecurity.employeecrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsecurity.employeecrud.DTO.BookDTO;
import springsecurity.employeecrud.DTO.PageDTO;
import springsecurity.employeecrud.DTO.StatusDTO;
import springsecurity.employeecrud.Entity.AuthorEntity;
import springsecurity.employeecrud.Entity.BookEntity;
import springsecurity.employeecrud.SearchFilters.BookFilter;
import springsecurity.employeecrud.Service.AuthorService;
import springsecurity.employeecrud.Service.BookService;
import springsecurity.employeecrud.Transformer.BookTransformer;
import springsecurity.employeecrud.Utils.AllUtils;
import springsecurity.employeecrud.Utils.PaginationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Saeed Shah
 */


@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @PostMapping(value = "/create")
    public ResponseEntity<StatusDTO> create(@RequestBody BookDTO bookDTO) {
        try {

            BookEntity bookEntity = BookTransformer.toEntity(bookDTO);
            if(bookDTO.getAuthorDTO()!=null){
                AuthorEntity authorEntity = authorService.findById(Long.parseLong(bookDTO.getAuthorDTO().getId()));
                if( authorEntity==null){
                    return new ResponseEntity(new StatusDTO(0,"Author not found"),HttpStatus.NOT_FOUND);
                }
                bookEntity.setAuthorEntity(authorEntity);

            }
            bookEntity.setStatus(true);
            bookService.create(bookEntity);

            return new ResponseEntity(new StatusDTO(1, " Added Successfully", BookTransformer.toDTO(bookEntity)), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception occurred! " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/update")
    public ResponseEntity<StatusDTO> update(@RequestBody BookDTO bookDTO) {

        try {

            BookEntity bookEntity = bookService.findById(Long.parseLong(bookDTO.getId()));
            if (bookEntity == null) {
                return new ResponseEntity(new StatusDTO(0, "Book not found"), HttpStatus.NOT_FOUND);
            }

            BookEntity bookEntity1 = BookTransformer.toEntity(bookDTO);
            if(bookDTO.getAuthorDTO()!=null){
                AuthorEntity authorEntity = authorService.findById(Long.parseLong(bookDTO.getAuthorDTO().getId()));
                if( authorEntity==null){
                    return new ResponseEntity(new StatusDTO(0,"Author not found"),HttpStatus.NOT_FOUND);
                }
                bookEntity1.setAuthorEntity(authorEntity);
            }
            bookEntity1.setStatus(true);
            bookService.update(bookEntity1);

            return new ResponseEntity(new StatusDTO(1, "Updated"), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {

        try {
            BookEntity bookEntity = bookService.findById(id);
            if (bookEntity != null) {
                bookService.delete(bookEntity);
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
            BookEntity bookEntity = bookService.findById(id);

            if (bookEntity != null) {
                BookDTO employeeDTO = BookTransformer.toDTO(bookEntity);
                return new ResponseEntity(employeeDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity(" book not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "/getAll")
    public List<BookDTO> getAll() {
        List<BookEntity> bookEntities = bookService.findAll();
        return BookTransformer.getDTOs(bookEntities);
    }

    @GetMapping(value = "/getAllByAuthor")
    public List<BookDTO> getAllByAuthor(@PathVariable Long id) {
        List<BookEntity> bookEntities = bookService.findAllByAuthorEntityId(id);
        if(bookEntities!=null && !bookEntities.isEmpty()){
            return BookTransformer.getDTOs(bookEntities);
        }

        return null;
    }

    @GetMapping(value = "/getAllByName")
    public List<BookDTO> getAllByName(@PathVariable String name) {
        List<BookEntity> bookEntities = bookService.findByName(name);
        if(bookEntities!=null && !bookEntities.isEmpty()){
            return BookTransformer.getDTOs(bookEntities);
        }

        return null;
    }
    @PostMapping(value = "/views")
    public PageDTO getAll(BookFilter filter,@RequestParam Map<String, String> params) {
        Page<BookEntity> page = bookService.findAllByFilterWithPaging(filter, AllUtils.createPageRequest(params));
        return new PageDTO(BookTransformer.getDTOs(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @PostMapping("/truncate")
    public ResponseEntity<StatusDTO> truncate(){
        try{
            List<BookEntity> bookEntities = bookService.findAll();
            if(!bookEntities.isEmpty()){
                for (BookEntity bookEntity: bookEntities){
                    bookService.delete(bookEntity);
                }
                return new ResponseEntity<StatusDTO>(new StatusDTO(1, "Truncated Successfully "), HttpStatus.OK);
            }else {
                return new ResponseEntity<StatusDTO>(new StatusDTO(0,"Books not found!"), HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            return new ResponseEntity<StatusDTO>(new StatusDTO(0, "Exception occurred: "+ex.getMessage()), HttpStatus.OK);
        }
    }
}
