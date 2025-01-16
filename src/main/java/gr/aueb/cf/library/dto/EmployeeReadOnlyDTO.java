package gr.aueb.cf.library.dto;

import gr.aueb.cf.library.core.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeReadOnlyDTO {


    private Long id;

    private String uuid;

    private String firstname;

    private String lastname;

    private String username;

//    private String email;

//    private Role role;

    private Boolean isActive;

    private Long userId;

    private UserReadOnlyDTO user;



}
