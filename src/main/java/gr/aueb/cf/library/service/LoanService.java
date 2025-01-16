package gr.aueb.cf.library.service;

import gr.aueb.cf.library.core.exceptions.ObjectAlreadyExistsException;
import gr.aueb.cf.library.core.exceptions.ObjectInvalidArgumentException;
import gr.aueb.cf.library.core.exceptions.ObjectNotFoundException;
import gr.aueb.cf.library.core.filters.LoanFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.core.specifications.LoanSpecification;
import gr.aueb.cf.library.dto.LoanInsertDTO;
import gr.aueb.cf.library.dto.LoanReadOnlyDTO;
import gr.aueb.cf.library.mapper.LoanMapper;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.User;
import gr.aueb.cf.library.repository.BookRepository;
import gr.aueb.cf.library.repository.LoanRepository;
import gr.aueb.cf.library.repository.UserRepository;
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
public class LoanService {

    private final LoanMapper loanMapper;
    @Autowired
    private final LoanRepository loanRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final UserRepository userRepository;



    public LoanReadOnlyDTO saveLoan(LoanInsertDTO loanInsertDTO)
            throws ObjectAlreadyExistsException, ObjectInvalidArgumentException, ObjectNotFoundException, IOException {

        Book book = bookRepository.findById(loanInsertDTO.getBook().getBookId())
                .orElseThrow(() -> new ObjectNotFoundException("Book", "Book with ID: " + loanInsertDTO.getBook().getBookId() + " not found"));

        User user = userRepository.findByUsername(loanInsertDTO.getUser().getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("User", "User with Email: " + loanInsertDTO.getUser().getEmail() + " not found"));

        if(loanRepository.findByUserIdAndBookId(book.getId(), user.getId()).isPresent()) {
            throw new ObjectAlreadyExistsException("Loan", "Book with" + book.getId() + " already loaned to user" + user.getId());
        }



        Loan loan = loanMapper.mapToLoanEntity(loanInsertDTO);
        loan.setBook(book);
        loan.setUser(user);
        loanRepository.save(loan);

        return loanMapper.mapToLoanReadOnlyDTO(loan);

    }

    public Page<LoanReadOnlyDTO> getPaginatedBorrowedBooks(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return loanRepository.findAll(pageable).map(loanMapper::mapToLoanReadOnlyDTO);
    }


    public Page<LoanReadOnlyDTO> getPaginatedSortedLoans(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return loanRepository.findAll(pageable).map(loanMapper::mapToLoanReadOnlyDTO);
    }

    public Paginated<LoanReadOnlyDTO> getLoanPaginated(LoanFilters filters) {
        var filtered = loanRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(loanMapper::mapToLoanReadOnlyDTO));
    }

    public List<LoanReadOnlyDTO> getLoansFiltered(LoanFilters filters) {
        return loanRepository.findAll(getSpecsFromFilters(filters))
                .stream().map(loanMapper::mapToLoanReadOnlyDTO).toList();
    }

    public Specification<Loan> getSpecsFromFilters(LoanFilters filters) {
        return Specification
                .where(LoanSpecification.loanTitle(filters.getBookTitle()))
                .and(LoanSpecification.loanDate(filters.getLoanDate()))
                .and(LoanSpecification.returnDate(filters.getReturnDate()));

    }


}
