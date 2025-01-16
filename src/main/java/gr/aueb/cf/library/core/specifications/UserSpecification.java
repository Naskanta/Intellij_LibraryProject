package gr.aueb.cf.library.core.specifications;

import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private UserSpecification() {

    }


    public static Specification<User> userUsername(String username) {
        return ((root, query, criteriaBuilder) -> {
            if (username == null || username.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("username"), username);
        });
    }

    public static Specification<User> userIsActive(Boolean isActive) {
        return ((root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("isActive"), isActive
            );
        });
    }
}
