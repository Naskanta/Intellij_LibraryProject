package gr.aueb.cf.library.dto;


import gr.aueb.cf.library.core.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeInsertDTO {

//    @NotNull(message = "Is Active must not be null")
//    private Boolean isActive;

    @NotNull(message = "Username details must not be null")
    private String username;

    @Pattern(regexp =  "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[@#$%!^&*]).{8,}$", message = "Invalid password")
    private String password;

    @NotEmpty(message = "Firstname must not be empty")
    private String firstname;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Lastname must not be empty")
    private String lastname;


    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private UserInsertDTO user;


}
