package springsecurity.employeecrud.SearchFilters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import springsecurity.employeecrud.Entity.AuthorEntity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuthorFilter implements Specification<AuthorEntity> {

    private String authorName;
    private String country;

    @Override
    public Predicate toPredicate(Root<AuthorEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (authorName != null && !authorName.equalsIgnoreCase("") && !authorName.equalsIgnoreCase("undefined")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("authorName")), "%" + authorName.toUpperCase() + "%"));
        }

        if (country != null && !country.equalsIgnoreCase("") && !country.equalsIgnoreCase("undefined")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("country")), "%" + authorName.toUpperCase() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
