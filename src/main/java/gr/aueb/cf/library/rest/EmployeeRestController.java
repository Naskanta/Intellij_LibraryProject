package gr.aueb.cf.library.rest;

import gr.aueb.cf.library.core.exceptions.*;
import gr.aueb.cf.library.core.filters.EmployeeFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.dto.EmployeeInsertDTO;
import gr.aueb.cf.library.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.library.mapper.EmployeeMapper;
import gr.aueb.cf.library.model.Employee;
import gr.aueb.cf.library.service.EmployeeService;
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
public class EmployeeRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);
    private final EmployeeService employeeService;


    @GetMapping("/employees")
    public ResponseEntity<Page<EmployeeReadOnlyDTO>> getPaginatedEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<EmployeeReadOnlyDTO> employeesPage = employeeService.getPaginatedEmployees(page, size);
        return new ResponseEntity<>(employeesPage, HttpStatus.OK);
    }


    @PostMapping("/employees/save")
    public ResponseEntity<EmployeeReadOnlyDTO> saveEmployee(
            @Valid @RequestBody EmployeeInsertDTO employeeInsertDTO, BindingResult bindingResult)
            throws ObjectInvalidArgumentException, ValidationException, ObjectAlreadyExistsException, ServerException, IOException {

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

            EmployeeReadOnlyDTO employeeReadOnlyDTO = employeeService.saveEmployee(employeeInsertDTO);
            return new ResponseEntity<>(employeeReadOnlyDTO, HttpStatus.OK);
    }

    @PostMapping("/employees/all")
    public ResponseEntity<List<EmployeeReadOnlyDTO>> getEmployees(@Nullable @RequestBody EmployeeFilters filters,
                                                                 Principal principal)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = EmployeeFilters.builder().build();
            return ResponseEntity.ok(employeeService.getEmployeesFiltered(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get employees.", e);
            throw e;
        }
    }

    @PostMapping("/employees/all/paginated")
    public ResponseEntity<Paginated<EmployeeReadOnlyDTO>> getEmployeesFilteredPaginated(@Nullable @RequestBody EmployeeFilters filters,
                                                                                       Principal principal)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = EmployeeFilters.builder().build();
            return ResponseEntity.ok(employeeService.getEmployeesFilteredPaginated(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get employees.", e);
            throw e;
        }
    }




}
