package gr.aueb.cf.library.service;

import gr.aueb.cf.library.core.exceptions.ObjectAlreadyExistsException;
import gr.aueb.cf.library.core.exceptions.ObjectInvalidArgumentException;
import gr.aueb.cf.library.core.filters.BookFilters;
import gr.aueb.cf.library.core.filters.EmployeeFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.core.specifications.BookSpecification;
import gr.aueb.cf.library.core.specifications.EmployeeSpecification;
import gr.aueb.cf.library.dto.BookInsertDTO;
import gr.aueb.cf.library.dto.BookReadOnlyDTO;
import gr.aueb.cf.library.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.library.dto.UserReadOnlyDTO;
import gr.aueb.cf.library.mapper.BookMapper;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Employee;
import gr.aueb.cf.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    private final BookMapper bookMapper;


    public BookReadOnlyDTO saveBook(BookInsertDTO bookInsertDTO)
            throws ObjectAlreadyExistsException, ObjectInvalidArgumentException, IOException {

        if (bookRepository.findByTitle(bookInsertDTO.getTitle()).isPresent()) {
            throw new ObjectAlreadyExistsException("Book", "Book with title: " + bookInsertDTO.getTitle() + " already exists");
        }

        if (bookRepository.findByIsbn(bookInsertDTO.getIsbn()).isPresent()) {
            throw new ObjectAlreadyExistsException("Book", "Book with Isbn: " + bookInsertDTO.getIsbn() + " already exists");
        }

        Book book = bookMapper.mapToBookEntity(bookInsertDTO);
        return bookMapper.mapToBookReadOnlyDTO(bookRepository.save(book));


    }


    public Page<BookReadOnlyDTO> getPaginatedBooks(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return bookRepository.findAll(pageable).map(bookMapper::mapToBookReadOnlyDTO);
    }

    public Page<BookReadOnlyDTO> getPaginatedSortedBooks(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable).map(bookMapper::mapToBookReadOnlyDTO);
    }

    public Paginated<BookReadOnlyDTO> getBooksFilteredPaginated(BookFilters filters) {
        var filtered = bookRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(bookMapper::mapToBookReadOnlyDTO));
    }

    public List<BookReadOnlyDTO> getBooksFiltered(BookFilters filters) {
        return bookRepository.findAll(getSpecsFromFilters(filters))
                .stream().map(bookMapper::mapToBookReadOnlyDTO).toList();
    }

    private Specification<Book> getSpecsFromFilters(BookFilters filters) {
        return Specification
                .where(BookSpecification.bookIsbn(filters.getIsbn()))
                .and(BookSpecification.bookPublisher(filters.getPublisher()))
                .and(BookSpecification.bookAuthor(filters.getAuthor()));
    }
}
