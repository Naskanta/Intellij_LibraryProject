package gr.aueb.cf.library.core.specifications;

import gr.aueb.cf.library.model.Loan;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class LoanSpecification {

    private LoanSpecification() {

    }

    public static Specification<Loan> loanTitle(String title) {
        return ((root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("book").get("title"), "%" + title + "%");
        });
    }

    public static Specification<Loan> loanDate(LocalDateTime loanDate) {
        return ((root, query, criteriaBuilder) -> {
            if (loanDate == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("loanDate"), loanDate);
        });
    }


    public static Specification<Loan> returnDate(LocalDateTime returnDate) {
        return ((root, query, criteriaBuilder) -> {
            if (returnDate == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("returnDate"), returnDate);
        });
    }

}




