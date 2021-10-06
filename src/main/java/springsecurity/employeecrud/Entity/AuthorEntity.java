package springsecurity.employeecrud.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Author")
@Where(clause = "status=1")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String authorName;

    private String country;
    private String totalPublications;
    private Boolean status;

    @OneToMany(mappedBy = "authorEntity" , fetch = FetchType.LAZY)
    private List<BookEntity> books;
}
