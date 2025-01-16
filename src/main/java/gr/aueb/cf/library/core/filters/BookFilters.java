package gr.aueb.cf.library.core.filters;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookFilters extends GenericFilters {

    @Nullable
    private String publisher;

    @Nullable
    private String author;

    @Nullable
    private String isbn;

}
