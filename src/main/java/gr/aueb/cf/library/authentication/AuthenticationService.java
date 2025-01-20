package gr.aueb.cf.library.authentication;

import gr.aueb.cf.library.core.exceptions.ObjectNotAuthorizedException;
import gr.aueb.cf.library.core.exceptions.ObjectNotFoundException;
import gr.aueb.cf.library.dto.AuthenticationRequestDTO;
import gr.aueb.cf.library.dto.AuthenticationResponseDTO;
import gr.aueb.cf.library.model.User;
import gr.aueb.cf.library.repository.UserRepository;
import gr.aueb.cf.library.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto)
            throws ObjectNotAuthorizedException {
        // Create an authentication token from username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ObjectNotAuthorizedException("User", "User not authorized"));

        // If authentication was successful, generate JWT token
        String token = jwtService.generateToken(authentication.getName(), user.getRole().name());
        return new AuthenticationResponseDTO(user.getFirstname(), user.getLastname(), token);
    }
}