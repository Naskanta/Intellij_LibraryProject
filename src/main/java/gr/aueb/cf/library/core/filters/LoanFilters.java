package gr.aueb.cf.library.core.filters;

import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoanFilters extends GenericFilters {


    @Nullable
    private String bookTitle;

    @Nullable
    private String username;

    private String author;

    @Nullable
    private LocalDateTime loanDate;

    @Nullable
    private LocalDateTime returnDate;
}
