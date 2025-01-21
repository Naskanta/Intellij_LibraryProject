package gr.aueb.cf.library.dto;

import gr.aueb.cf.library.core.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

//    @NotNull(message = "Is Active must not be null")
//    private Boolean isActive;

    @NotNull(message = "Firstname must not be null")
    private String firstname;

    @NotNull(message = "Lastname must not be null")
    private String lastname;

    @NotEmpty(message = "Invalid username")
    private String username;

    @Pattern(regexp =  "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[@#$%!^&*]).{8,}$", message = "Invalid password")
    private String password;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must not be null")
    private Role role;


}
