package springsecurity.employeecrud.Entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Employee")
@Where(clause = "status=1")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "profession")
    private String profession;

    @Column(name = "status")
    private Boolean status;


}
