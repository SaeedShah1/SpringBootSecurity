package springsecurity.employeecrud.Transformer;

import springsecurity.employeecrud.DTO.BookDTO;
import springsecurity.employeecrud.Entity.BookEntity;
import springsecurity.employeecrud.Utils.AllUtils;

import java.util.ArrayList;
import java.util.List;

public class BookTransformer {

    public static BookEntity toEntity(BookDTO dto){
        BookEntity entity = new BookEntity();

        if(dto.getId()!=null){
            entity.setId(Long.parseLong(dto.getId()));
        }

        if(dto.getName()!=null){
            entity.setName(dto.getName());
        }
        if(dto.getPublishDate()!=null){
            entity.setPublishDate(AllUtils.stringToDate(dto.getPublishDate()));
        }

        if(dto.getStatus()!=null){
            entity.setStatus(Boolean.parseBoolean(dto.getStatus()));
        }

        if(dto.getAuthorDTO()!=null){
            entity.setAuthorEntity(AuthorTransformer.toEntity(dto.getAuthorDTO()));
        }


        return entity;
    }


    public static BookDTO toDTO(BookEntity  entity){
        BookDTO bookDTO = new BookDTO();

        if(entity.getId()!=null){
            bookDTO.setId(entity.getId().toString());
        }

        if(entity.getName()!=null){
            bookDTO.setName(entity.getName());
        }

        if(entity.getPublishDate()!=null){
            bookDTO.setPublishDate(entity.getPublishDate().toString());
        }

        if(entity.getStatus()!=null){
            bookDTO.setStatus(entity.getStatus().toString());
        }

        if(entity.getAuthorEntity()!=null){
            bookDTO.setAuthorDTO(AuthorTransformer.toDTO(entity.getAuthorEntity()));
        }

        return bookDTO;
    }

    public static BookDTO toDTOForAuthor(BookEntity entity){
        BookDTO d = new BookDTO();

        if(entity.getId()!=null){
            d.setId(entity.getId().toString());
        }

        if(entity.getName()!=null){
            d.setName(entity.getName());
        }

        if(entity.getPublishDate()!=null){
            d.setPublishDate(entity.getPublishDate().toString());
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
