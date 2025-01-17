package gr.aueb.cf.library.mapper;

import gr.aueb.cf.library.dto.UserInsertDTO;
import gr.aueb.cf.library.dto.UserReadOnlyDTO;
import gr.aueb.cf.library.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

//    private final PasswordEncoder passwordEncoder;

    public User mapToUserEntity(UserInsertDTO userInsertDTO) {
        User user = new User();
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(userInsertDTO.getPassword());
        user.setEmail(userInsertDTO.getEmail());
        user.setFirstname(userInsertDTO.getFirstname());
        user.setLastname(userInsertDTO.getLastname());
        user.setDateOfBirth(userInsertDTO.getDateOfBirth());
        user.setRole(userInsertDTO.getRole());
//
//        String encodedPassword = passwordEncoder.encode(userInsertDTO.getPassword());
//        user.setPassword(encodedPassword);
        return user;
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        UserReadOnlyDTO userReadOnlyDTO = new UserReadOnlyDTO();
        userReadOnlyDTO.setUuid(user.getUuid());
        userReadOnlyDTO.setUsername(user.getUsername());
        userReadOnlyDTO.setEmail(user.getEmail());
        userReadOnlyDTO.setFirstname(user.getFirstname());
        userReadOnlyDTO.setLastname(user.getLastname());
        userReadOnlyDTO.setRole(user.getRole());
        userReadOnlyDTO.setIsActive(user.getIsActive());


        return userReadOnlyDTO;
    }
}
