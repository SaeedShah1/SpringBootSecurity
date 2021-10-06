package springsecurity.employeecrud.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorDTO {

    private String id;
    private String authorName;
    private String country;
    private String totalPublications;
    private String status;

    private List<BookDTO> bookDTOs;
}
