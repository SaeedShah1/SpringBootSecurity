package springsecurity.employeecrud.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String id;
    private String name;
    private AuthorDTO authorDTO;
    private String status;
    private String publishDate;

}
