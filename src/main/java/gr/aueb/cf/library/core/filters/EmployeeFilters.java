package gr.aueb.cf.library.core.filters;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EmployeeFilters extends GenericFilters {

    @Nullable
    private String uuid;

    @Nullable
    private String lastname;

    @Nullable
    private Boolean active;


}
