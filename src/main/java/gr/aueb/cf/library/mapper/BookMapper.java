package gr.aueb.cf.library.mapper;

import gr.aueb.cf.library.dto.BookInsertDTO;
import gr.aueb.cf.library.dto.BookReadOnlyDTO;
import gr.aueb.cf.library.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    public Book mapToBookEntity(BookInsertDTO bookInsertDTO) {
        Book book = new Book();
        book.setTitle(bookInsertDTO.getTitle());
        book.setAuthor(bookInsertDTO.getAuthor());
        book.setPublisher(bookInsertDTO.getPublisher());
        book.setIsbn(bookInsertDTO.getIsbn());
        return book;
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        BookReadOnlyDTO bookReadOnlyDTO = new BookReadOnlyDTO();
        bookReadOnlyDTO.setAuthor(book.getAuthor());
        bookReadOnlyDTO.setTitle(book.getTitle());
        bookReadOnlyDTO.setPublisher(book.getPublisher());
        bookReadOnlyDTO.setIsbn(book.getIsbn());
        return bookReadOnlyDTO;
    }


}
