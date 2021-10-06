package springsecurity.employeecrud.Transformer;

import springsecurity.employeecrud.DTO.AuthorDTO;
import springsecurity.employeecrud.DTO.BookDTO;
import springsecurity.employeecrud.Entity.AuthorEntity;
import springsecurity.employeecrud.Entity.BookEntity;
import java.util.ArrayList;
import java.util.List;

public class AuthorTransformer {

    public static AuthorEntity toEntity(AuthorDTO dto){
        AuthorEntity entity = new AuthorEntity();

        if(dto.getId()!=null){
            entity.setId(Long.parseLong(dto.getId()));
        }

        if(dto.getAuthorName()!=null){
            entity.setAuthorName(dto.getAuthorName());
        }
        if(dto.getCountry()!=null){
            entity.setCountry(dto.getCountry());
        }

        if(dto.getStatus()!=null){
            entity.setStatus(Boolean.parseBoolean(dto.getStatus()));
        }

        if(dto.getTotalPublications()!=null){
          entity.setTotalPublications(dto.getTotalPublications());
        }


        return entity;
    }


    public static AuthorDTO toDTO(AuthorEntity  entity){
        AuthorDTO authorDTO = new AuthorDTO();

        if(entity.getId()!=null){
            authorDTO.setId(entity.getId().toString());
        }

        if(entity.getAuthorName()!=null){
            authorDTO.setAuthorName(entity.getAuthorName());
        }
        if(entity.getCountry()!=null){
            authorDTO.setCountry(entity.getCountry());
        }
        if(entity.getTotalPublications()!=null){
            authorDTO.setTotalPublications(entity.getTotalPublications());
        }

        if(entity.getStatus()!=null){
            authorDTO.setStatus(entity.getStatus().toString());
        }
        if(entity.getBooks()!=null){
            List<BookDTO> bookDTOS= new ArrayList<>();
            for(BookEntity bookEntity:entity.getBooks()){
                bookDTOS.add(BookTransformer.toDTOForAuthor(bookEntity));

            }
            authorDTO.setBookDTOs(bookDTOS);
        }

        return authorDTO;
    }

    public static List<AuthorDTO> getDTOs(List<AuthorEntity> authorEntities) {
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        authorEntities.forEach(authorEntity -> {
            authorDTOS.add(toDTO(authorEntity));
        });
        return authorDTOS;
    }
}
