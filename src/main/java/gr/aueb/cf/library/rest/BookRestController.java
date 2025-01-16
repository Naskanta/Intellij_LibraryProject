package gr.aueb.cf.library.rest;


import gr.aueb.cf.library.core.exceptions.*;
import gr.aueb.cf.library.core.filters.BookFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.core.filters.UserFilters;
import gr.aueb.cf.library.dto.BookInsertDTO;
import gr.aueb.cf.library.dto.BookReadOnlyDTO;
import gr.aueb.cf.library.dto.UserInsertDTO;
import gr.aueb.cf.library.dto.UserReadOnlyDTO;
import gr.aueb.cf.library.service.BookService;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);
    private final BookService bookService;


    @GetMapping("/books")
    public ResponseEntity<Page<BookReadOnlyDTO>> getPaginatedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BookReadOnlyDTO> booksPage = bookService.getPaginatedBooks(page, size);
        return new ResponseEntity<>(booksPage, HttpStatus.OK);
    }

    @PostMapping("/books/save")
    public ResponseEntity<BookReadOnlyDTO> saveBook(
            @Valid @RequestBody BookInsertDTO bookInsertDTO, BindingResult bindingResult)
            throws ObjectInvalidArgumentException, ValidationException, ObjectAlreadyExistsException, ServerException, IOException {

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        BookReadOnlyDTO bookReadOnlyDTO = bookService.saveBook(bookInsertDTO);
        return new ResponseEntity<>(bookReadOnlyDTO, HttpStatus.OK);
    }

    @PostMapping("/books/all")
    public ResponseEntity<List<BookReadOnlyDTO>> getBooks(@Nullable @RequestBody BookFilters filters,
                                                          Principal principal)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = BookFilters.builder().build();
            return ResponseEntity.ok(bookService.getBooksFiltered(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get books.", e);
            throw e;
        }
    }

    @PostMapping("/books/all/paginated")
    public ResponseEntity<Paginated<BookReadOnlyDTO>> getBooksFilteredPaginated(@Nullable @RequestBody BookFilters filters,
                                                                                Principal principal)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = BookFilters.builder().build();
            return ResponseEntity.ok(bookService.getBooksFilteredPaginated(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get books.", e);
            throw e;
        }
    }
}
