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

    private String uuid;
    private String username;
    private String email;
    private Boolean isActive;
}
