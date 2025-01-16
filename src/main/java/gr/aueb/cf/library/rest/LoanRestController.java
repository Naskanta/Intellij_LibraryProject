package gr.aueb.cf.library.rest;

import gr.aueb.cf.library.core.exceptions.*;
import gr.aueb.cf.library.core.filters.LoanFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.dto.LoanInsertDTO;
import gr.aueb.cf.library.dto.LoanReadOnlyDTO;
import gr.aueb.cf.library.service.LoanService;
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
public class LoanRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanRestController.class);
    private final LoanService loanService;

    @GetMapping("/loans")
    public ResponseEntity<Page<LoanReadOnlyDTO>> getPaginatedBorrowedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<LoanReadOnlyDTO> borrowedBooksPage = loanService.getPaginatedBorrowedBooks(page, size);
        return new ResponseEntity<>(borrowedBooksPage, HttpStatus.OK);
    }

    @PostMapping("/loans/save")
    public ResponseEntity<LoanReadOnlyDTO> saveLoan(
            @Valid @RequestBody  LoanInsertDTO loanInsertDTO, BindingResult bindingResult)
            throws ObjectInvalidArgumentException, ValidationException, ObjectAlreadyExistsException, ServerException, IOException, ObjectNotFoundException {

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        LoanReadOnlyDTO loanReadOnlyDTO = loanService.saveLoan(loanInsertDTO);
        return new ResponseEntity<>(loanReadOnlyDTO, HttpStatus.OK);
    }

    @PostMapping("/loans/all")
    public ResponseEntity<List<LoanReadOnlyDTO>> getLoans(@Nullable @RequestBody LoanFilters filters,
                                                                  Principal principal)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = LoanFilters.builder().build();
            return ResponseEntity.ok(loanService.getLoansFiltered(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get loans.", e);
            throw e;
        }
    }


    @PostMapping("/loans/all/paginated")
    public ResponseEntity<Paginated<LoanReadOnlyDTO>> getLoansFilteredPaginated(@Nullable @RequestBody LoanFilters filters,
                                                                                        Principal principal)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = LoanFilters.builder().build();
            return ResponseEntity.ok(loanService.getLoanPaginated(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get borrowed books.", e);
            throw e;
        }
    }
}
