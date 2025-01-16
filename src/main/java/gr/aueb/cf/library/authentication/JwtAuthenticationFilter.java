package gr.aueb.cf.library.authentication;

import gr.aueb.cf.library.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
             HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractSubject(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }
    }


//        try {
//            username = jwtService.extractSubject(token);
//            userRole = jwtService.getStringClaim(token, "role");
//
//            System.out.println("wwww JWT extracted.. ROLE: " + userRole);
//            System.out.println("wwww JWT extracted.. ID: " + username);
////            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//                if (jwtService.isTokenValid(token, userDetails)) {
//                    System.out.println("Token is valid: " + request.getRequestURI());
//                    System.out.println("userDetails getAuthorities: " + userDetails.getAuthorities());
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                } else {
//                    System.out.println("Token is NOT valid: " + request.getRequestURI());
//                }
//            }
//        } catch (ExpiredJwtException e) {
//            LOGGER.warn("WARN: Expired token ", e);
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.setContentType("application/json");
//            String jsonBody = "{\"code\": \"expiredToken\", \"message\": \"" + e.getMessage() + "\"}";
//            response.getWriter().write(jsonBody);
//            return;
//        } catch (Exception e) {
//            LOGGER.warn("ERROR: something went wrong while parsing JWT", e);
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            response.setContentType("application/json");
//            String jsonBody = "{\"code\": \"invalidToken\", \"description\": \"" + e.getMessage() + "\"}";
//            response.getWriter().write(jsonBody);
//            return;
//        }
//        filterChain.doFilter(request, response);
//    }
