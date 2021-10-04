package springsecurity.employeecrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsecurity.employeecrud.DTO.BookDTO;
import springsecurity.employeecrud.DTO.StatusDTO;
import springsecurity.employeecrud.Entity.BookEntity;
import springsecurity.employeecrud.Service.BookService;
import springsecurity.employeecrud.Transformer.BookTransformer;

import java.util.List;

/**
 *
 * @author Saeed Shah
 */


@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping(value = "/create")
    public ResponseEntity<StatusDTO> create(@ModelAttribute BookDTO bookDTO) {
        try {

            BookEntity bookEntity = BookTransformer.toEntity(bookDTO);
            bookEntity.setStatus(true);
            bookService.create(bookEntity);


            return new ResponseEntity(new StatusDTO(1, " Added Successfully", BookTransformer.toDTO(bookEntity)), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception occurred! " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/update")
    public ResponseEntity<StatusDTO> update(@ModelAttribute BookDTO bookDTO) {

        try {

            BookEntity bookEntity = bookService.findById(Long.parseLong(bookDTO.getId()));
            if (bookEntity == null) {
                return new ResponseEntity(new StatusDTO(0, "Book not found"), HttpStatus.NOT_FOUND);
            }
            BookEntity bookEntity1 = BookTransformer.toEntity(bookDTO);

            bookEntity1.setStatus(true);
            bookService.create(bookEntity1);

            return new ResponseEntity(new StatusDTO(1, "Updated"), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/delete")
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
