package gr.aueb.cf.library.service;

import gr.aueb.cf.library.core.exceptions.ObjectAlreadyExistsException;
import gr.aueb.cf.library.core.exceptions.ObjectInvalidArgumentException;
import gr.aueb.cf.library.core.filters.EmployeeFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.core.specifications.EmployeeSpecification;
import gr.aueb.cf.library.dto.EmployeeInsertDTO;
import gr.aueb.cf.library.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.library.mapper.EmployeeMapper;
import gr.aueb.cf.library.model.Employee;
import gr.aueb.cf.library.repository.EmployeeRepository;
import gr.aueb.cf.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeMapper employeeMapper;


    @Transactional(rollbackOn = Exception.class)
    public EmployeeReadOnlyDTO saveEmployee(EmployeeInsertDTO employeeInsertDTO)
            throws ObjectAlreadyExistsException, ObjectInvalidArgumentException, IOException {

        if (userRepository.findByEmail(employeeInsertDTO.getUser().getEmail()).isPresent()) {
            throw new ObjectAlreadyExistsException("User", "User with email: " + employeeInsertDTO.getUser().getEmail() + " already exists");
        }

        if (employeeRepository.findByUsername(employeeInsertDTO.getUsername()).isPresent()) {
            throw new ObjectAlreadyExistsException("User", "User with username: " + employeeInsertDTO.getUsername() + " already exists");
        }

        if (employeeRepository.findByLastname(employeeInsertDTO.getLastname()).isPresent()) {
            throw new ObjectAlreadyExistsException("User", "User with lastname: " + employeeInsertDTO.getLastname() + " already exists");
        }

        if (userRepository.findByUsername(employeeInsertDTO.getUser().getUsername()).isPresent()) {
            throw new ObjectAlreadyExistsException("User", "User with username: " + employeeInsertDTO.getUsername() + " already exists");
        }

        Employee employee = employeeMapper.mapToEmployeeEntity(employeeInsertDTO);

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeReadOnlyDTO(savedEmployee);


    }

    @Transactional
    public Page<EmployeeReadOnlyDTO> getPaginatedEmployees(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return employeeRepository.findAll(pageable).map(employeeMapper::mapToEmployeeReadOnlyDTO);
    }

    @Transactional
    public Page<EmployeeReadOnlyDTO> getPaginatedSortedEmployees(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable).map(employeeMapper::mapToEmployeeReadOnlyDTO);
    }


    public Paginated<EmployeeReadOnlyDTO> getEmployeesFilteredPaginated(EmployeeFilters filters) {
        var filtered = employeeRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(employeeMapper::mapToEmployeeReadOnlyDTO));
    }

    public List<EmployeeReadOnlyDTO> getEmployeesFiltered(EmployeeFilters filters) {
        return employeeRepository.findAll(getSpecsFromFilters(filters))
                .stream().map(employeeMapper::mapToEmployeeReadOnlyDTO).toList();
    }

    private Specification<Employee> getSpecsFromFilters(EmployeeFilters filters) {
        return Specification
                .where(EmployeeSpecification.employeeUserUuid(filters.getUuid()))
                .and(EmployeeSpecification.employeeIsActive(filters.getActive()));
    }

}
