package springsecurity.employeecrud.Transformer;

import springsecurity.employeecrud.DTO.BookDTO;
import springsecurity.employeecrud.Entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookTransformer {

    public static BookEntity toEntity(BookDTO dto){
        BookEntity entity = new BookEntity();

        if(dto.getId()!=null){
            entity.setId(Long.parseLong(dto.getId()));
        }

        if(dto.getAuthor()!=null){
            entity.setAuthor(dto.getAuthor());
        }

        if(dto.getName()!=null){
            entity.setName(dto.getName());
        }


        if(dto.getStatus()!=null){
            entity.setStatus(Boolean.parseBoolean(dto.getStatus()));
        }


        return entity;
    }


    public static BookDTO toDTO(BookEntity  entity){
        BookDTO d = new BookDTO();

        if(entity.getId()!=null){
            d.setId(entity.getId().toString());
        }


        if(entity.getName()!=null){
            d.setName(entity.getName());
        }
        if(entity.getAuthor()!=null){
            d.setAuthor(entity.getAuthor());
        }


        if(entity.getStatus()!=null){
            d.setStatus(entity.getStatus().toString());
        }


        return d;
    }

    public static List<BookDTO> getDTOs(List<BookEntity> bookEntities) {
        List<BookDTO> bookDTOS = new ArrayList<>();
        bookEntities.forEach(bookEntity -> {
            bookDTOS.add(toDTO(bookEntity));
        });
        return bookDTOS;
    }

}
