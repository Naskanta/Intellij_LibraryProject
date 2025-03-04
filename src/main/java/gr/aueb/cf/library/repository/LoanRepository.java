package gr.aueb.cf.library.repository;

import gr.aueb.cf.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    Optional<Loan> findByUserUsernameAndBookTitle(String username, String title);

}
