package gr.aueb.cf.library.service;

import gr.aueb.cf.library.core.enums.Role;
import gr.aueb.cf.library.core.exceptions.ObjectAlreadyExistsException;
import gr.aueb.cf.library.core.exceptions.ObjectInvalidArgumentException;
import gr.aueb.cf.library.core.filters.EmployeeFilters;
import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.core.filters.UserFilters;
import gr.aueb.cf.library.core.specifications.EmployeeSpecification;
import gr.aueb.cf.library.core.specifications.UserSpecification;
import gr.aueb.cf.library.dto.EmployeeReadOnlyDTO;
import gr.aueb.cf.library.dto.UserInsertDTO;
import gr.aueb.cf.library.dto.UserReadOnlyDTO;
import gr.aueb.cf.library.mapper.UserMapper;
import gr.aueb.cf.library.model.Employee;
import gr.aueb.cf.library.model.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private  final  EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO)
            throws ObjectAlreadyExistsException, ObjectInvalidArgumentException, IOException {


        if (userRepository.findByUsername(userInsertDTO.getUsername()).isPresent()) {
            throw new ObjectAlreadyExistsException("User", "User with username: " + userInsertDTO.getUsername() + " already exists");
        }

        User user = userMapper.mapToUserEntity(userInsertDTO);
        User savedUser = userRepository.save(user);
        return userMapper.mapToUserReadOnlyDTO(savedUser);
    }

    @Transactional
    public Page<UserReadOnlyDTO> getPaginatedUsers(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return userRepository.findAll(pageable).map(userMapper::mapToUserReadOnlyDTO);
    }

    @Transactional
    public Paginated<UserReadOnlyDTO> getUsersFilteredPaginated(UserFilters filters) {
        var filtered = userRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(userMapper::mapToUserReadOnlyDTO));
    }

    @Transactional
    public List<UserReadOnlyDTO> getUsersFiltered(UserFilters filters) {
        return userRepository.findAll(getSpecsFromFilters(filters))
                .stream().map(userMapper::mapToUserReadOnlyDTO).toList();
    }


    private Specification<User> getSpecsFromFilters(UserFilters filters) {
        return Specification
                .where(UserSpecification.userUsername(filters.getUsername()))
                .and(UserSpecification.userIsActive(filters.getActive()));

    }


}
