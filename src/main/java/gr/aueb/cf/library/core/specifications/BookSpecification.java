package gr.aueb.cf.library.core.specifications;

import gr.aueb.cf.library.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    private BookSpecification() {

    }

    public static Specification<Book> bookIsbn(String isbn) {
        return ((root, query, criteriaBuilder) -> {
            if (isbn == null || isbn.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("isbn"), isbn);
        });
    }

    public static Specification<Book> bookAuthor(String author) {
        return ((root, query, criteriaBuilder) -> {
            if (author == null || author.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("title"), author);
        });
    }

    public static Specification<Book> bookPublisher(String publisher) {
        return ((root, query, criteriaBuilder) -> {
            if (publisher == null || publisher.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("publisher"), publisher);
        });
    }


}
