package gr.aueb.cf.library.mapper;

import gr.aueb.cf.library.dto.LoanInsertDTO;
import gr.aueb.cf.library.dto.LoanReadOnlyDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    public LoanReadOnlyDTO mapToLoanReadOnlyDTO(Loan loan) {
        LoanReadOnlyDTO loanReadOnlyDTO = new LoanReadOnlyDTO();
        loanReadOnlyDTO.setId(String.valueOf(loan.getId()));
        loanReadOnlyDTO.setBookId(String.valueOf(loan.getBook().getId()));  // Σύνδεση με το βιβλίο
        loanReadOnlyDTO.setUserId(loan.getUser().getId());  // Σύνδεση με τον χρήστη
        loanReadOnlyDTO.setLoanDate(loan.getLoanDate());
        loanReadOnlyDTO.setReturnDate(loan.getReturnDate());
        return loanReadOnlyDTO;
    }

    public Loan mapToLoanEntity(LoanInsertDTO loanInsertDTO) {
        Loan loan = new Loan();
        loan.setLoanDate(loanInsertDTO.getLoanDate());
        loan.setReturnDate(loanInsertDTO.getReturnDate());


        Book book = new Book();
        book.setId(loanInsertDTO.getBook().getBookId());

        User user = new User();
        user.setUsername(loanInsertDTO.getUser().getUsername());

        return loan;
    }

}
