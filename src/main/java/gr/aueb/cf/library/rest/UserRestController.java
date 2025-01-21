package gr.aueb.cf.library.rest;


import gr.aueb.cf.library.core.exceptions.*;

import gr.aueb.cf.library.core.filters.Paginated;
import gr.aueb.cf.library.core.filters.UserFilters;

import gr.aueb.cf.library.dto.UserInsertDTO;
import gr.aueb.cf.library.dto.UserReadOnlyDTO;
import gr.aueb.cf.library.service.UserService;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<Page<UserReadOnlyDTO>> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<UserReadOnlyDTO> usersPage = userService.getPaginatedUsers(page, size);
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }


    @PostMapping("/users/save")
    public ResponseEntity<UserReadOnlyDTO> saveUser(
            @Valid @RequestBody UserInsertDTO userInsertDTO, BindingResult bindingResult)
            throws ObjectInvalidArgumentException, ValidationException, ObjectAlreadyExistsException, ServerException, IOException {

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }


        UserReadOnlyDTO userReadOnlyDTO = userService.saveUser(userInsertDTO);

        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    @PostMapping("/users/all")
    public ResponseEntity<List<UserReadOnlyDTO>> getUsers(@Nullable @RequestBody UserFilters filters )
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = UserFilters.builder().build();
            return ResponseEntity.ok(userService.getUsersFiltered(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get users.", e);
            throw e;
        }
    }

    @PostMapping("/users/all/paginated")
    public ResponseEntity<Paginated<UserReadOnlyDTO>> getUsersFilteredPaginated(@Nullable @RequestBody UserFilters filters)
            throws ObjectNotFoundException, ObjectNotAuthorizedException {
        try {
            if (filters == null) filters = UserFilters.builder().build();
            return ResponseEntity.ok(userService.getUsersFilteredPaginated(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get users.", e);
            throw e;
        }
    }
}
