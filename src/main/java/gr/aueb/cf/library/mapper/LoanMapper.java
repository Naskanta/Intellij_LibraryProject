package gr.aueb.cf.library.mapper;

import gr.aueb.cf.library.dto.*;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    public Loan mapToLoanEntity(LoanInsertDTO loanInsertDTO) {
        Loan loan = new Loan();

        loan.setLoanDate(loanInsertDTO.getLoanDate());
        loan.setReturnDate(loanInsertDTO.getReturnDate());


        BookInsertDTO bookInsertDTO = new BookInsertDTO();
        Book book = new Book();

        book.setIsbn(bookInsertDTO.getIsbn());
        loan.setBook(book);

        UserInsertDTO userInsertDTO = new UserInsertDTO();
        User user = new User();

        user.setUsername(userInsertDTO.getUsername());
        loan.setUser(user);

        return loan;
    }



    public LoanReadOnlyDTO mapToLoanReadOnlyDTO(Loan loan) {
        LoanReadOnlyDTO loanReadOnlyDTO = new LoanReadOnlyDTO();

        loanReadOnlyDTO.setId(String.valueOf(loan.getId()));
        loanReadOnlyDTO.setReturnDate(loan.getReturnDate());
        loanReadOnlyDTO.setLoanDate(loan.getLoanDate());

        BookReadOnlyDTO bookReadOnlyDTO = new BookReadOnlyDTO();

        bookReadOnlyDTO.setIsbn(loan.getBook().getIsbn());
        loanReadOnlyDTO.setBook(bookReadOnlyDTO);

        UserReadOnlyDTO userReadOnlyDTO = new UserReadOnlyDTO();

        userReadOnlyDTO.setUsername(loan.getUser().getUsername());
        loanReadOnlyDTO.setUser(userReadOnlyDTO);
        return loanReadOnlyDTO;
    }



}
