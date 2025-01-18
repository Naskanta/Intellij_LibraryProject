package gr.aueb.cf.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookInsertDTO {


    @NotNull(message = "Title must not be empty")
    private String title;

    @NotNull(message = "Author must not be empty")
    private String author;

    @NotNull(message = "Publisher must not be empty")
    private String publisher;

    @NotNull(message = "Isbn cannot be empty")
    private String isbn;
}
