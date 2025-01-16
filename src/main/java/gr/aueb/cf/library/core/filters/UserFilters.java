package gr.aueb.cf.library.core.filters;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserFilters extends GenericFilters {

    @Nullable
    private String uuid;

    @Nullable
    private String username;

    @Nullable
    private Boolean active;



}