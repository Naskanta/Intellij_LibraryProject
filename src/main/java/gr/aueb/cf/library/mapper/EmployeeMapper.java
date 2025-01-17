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

//    private final PasswordEncoder passwordEncoder;

    public EmployeeReadOnlyDTO mapToEmployeeReadOnlyDTO(Employee employee) {
        EmployeeReadOnlyDTO employeeReadOnlyDTO = new EmployeeReadOnlyDTO();
        employeeReadOnlyDTO.setId(employee.getId());
        employeeReadOnlyDTO.setFirstname(employee.getFirstname());
        employeeReadOnlyDTO.setIsActive(employee.getIsActive());

        UserReadOnlyDTO userDTO = new UserReadOnlyDTO();
        userDTO.setFirstname(employee.getUser().getFirstname());
        userDTO.setLastname(employee.getUser().getLastname());
        userDTO.setEmail(employee.getUser().getEmail());
        employeeReadOnlyDTO.setUser(userDTO);

        return employeeReadOnlyDTO;
    }

    public Employee mapToEmployeeEntity(EmployeeInsertDTO employeeInsertDTO) {
        Employee employee = new Employee();
        employee.setFirstname(employeeInsertDTO.getFirstname());
        employee.setEmail(employeeInsertDTO.getEmail());
        employee.setRole(employeeInsertDTO.getRole());

        UserInsertDTO userInsertDTO = employeeInsertDTO.getUser();
        User user = new User();
        user.setLastname(userInsertDTO.getLastname());
        user.setPassword(userInsertDTO.getPassword());
        user.setUsername(userInsertDTO.getUsername());
        employee.setUser(user);


        return employee;
    }


}
