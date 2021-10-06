package springsecurity.employeecrud.SearchFilters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import springsecurity.employeecrud.Entity.BookEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookFilter implements Specification<BookEntity> {

    private String author;
    private String name;

    @Override
    public Predicate toPredicate(Root<BookEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (author != null && !author.equalsIgnoreCase("") && !author.equalsIgnoreCase("undefined")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("author")), "%" + author.toUpperCase() + "%"));
        }

        if (name != null && !name.equalsIgnoreCase("") && !name.equalsIgnoreCase("undefined")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
