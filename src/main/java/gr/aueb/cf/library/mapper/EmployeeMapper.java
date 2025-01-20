package gr.aueb.cf.library.mapper;

import gr.aueb.cf.library.dto.EmployeeInsertDTO;
import gr.aueb.cf.library.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.library.dto.UserInsertDTO;
import gr.aueb.cf.library.dto.UserReadOnlyDTO;
import gr.aueb.cf.library.model.Employee;
import gr.aueb.cf.library.model.User;
import gr.aueb.cf.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final PasswordEncoder passwordEncoder;


    public Employee mapToEmployeeEntity(EmployeeInsertDTO employeeInsertDTO) {
        Employee employee = new Employee();

        employee.setFirstname(employeeInsertDTO.getFirstname());
        employee.setLastname(employeeInsertDTO.getLastname());
        employee.setIsActive(employeeInsertDTO.getIsActive());



        UserInsertDTO userInsertDTO = employeeInsertDTO.getUser();
        User user = new User();

        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
        user.setEmail(userInsertDTO.getEmail());
        user.setDateOfBirth(userInsertDTO.getDateOfBirth());
        user.setRole(userInsertDTO.getRole());
        employee.setUser(user);


        return employee;
    }


    public EmployeeReadOnlyDTO mapToEmployeeReadOnlyDTO(Employee employee) {
        EmployeeReadOnlyDTO employeeReadOnlyDTO = new EmployeeReadOnlyDTO();

        employeeReadOnlyDTO.setId(employee.getId());
        employeeReadOnlyDTO.setUuid(employee.getUuid());
        employeeReadOnlyDTO.setIsActive(employee.getIsActive());

        UserReadOnlyDTO userDTO = new UserReadOnlyDTO();

        userDTO.setUsername(employee.getUser().getUsername());
        userDTO.setEmail(employee.getUser().getEmail());
        employeeReadOnlyDTO.setUser(userDTO);

        return employeeReadOnlyDTO;
    }




}
