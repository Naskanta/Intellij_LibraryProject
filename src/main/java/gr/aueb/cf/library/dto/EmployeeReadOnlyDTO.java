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

    private Boolean isActive;

    private UserReadOnlyDTO user;



}
