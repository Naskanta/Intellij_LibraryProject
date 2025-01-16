package gr.aueb.cf.library.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanInsertDTO {

    @NotEmpty(message = "Book ID is mandatory")
    private BookInsertDTO book;

    @NotEmpty(message = "User ID is mandatory")
    private UserInsertDTO user;

    @NotNull(message = "Loan date is mandatory")
    private LocalDateTime loanDate;

    @NotNull(message = "Return date is mandatory")
    private LocalDateTime returnDate;
}
