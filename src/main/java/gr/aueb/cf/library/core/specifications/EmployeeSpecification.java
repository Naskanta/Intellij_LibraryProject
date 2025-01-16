package gr.aueb.cf.library.core.specifications;

import gr.aueb.cf.library.model.Employee;
import gr.aueb.cf.library.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> employeeUserUuid(String username) {
        return((root, query, criteriaBuilder) -> {
            if (username == null || username.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            Join<Employee, User> user = root.join("user");
            return criteriaBuilder.equal(user.get("username"), username);
        });
    }

    public static Specification<Employee> employeeIsActive(Boolean isActive){
            return ((root, query, criteriaBuilder) -> {
                if (isActive == null ) {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }

                Join<Employee, User> user = root.join("user");
                return criteriaBuilder.equal(user.get("isActive"), isActive);
            });
    }

}
