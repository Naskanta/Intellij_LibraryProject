package gr.aueb.cf.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="books")
public class Book extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private  String publisher;

    @NotNull(message = "Isbn cannot be null")
    private String isbn;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans;

}
