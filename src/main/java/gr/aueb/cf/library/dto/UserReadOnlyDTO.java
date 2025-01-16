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
public class UserReadOnlyDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String uuid;
    private String username;
    private Boolean isActive;
    private Role role;
}
