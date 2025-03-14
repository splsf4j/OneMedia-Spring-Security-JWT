package org.vladislavb.onemediatesttask.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vladislavb.onemediatesttask.security.CustomUserDetails;
import org.vladislavb.onemediatesttask.security.CustomUserServiceImpl;

import java.io.IOException;

/**
 * JwtFilter is a custom filter that intercepts incoming HTTP requests to validate JWT tokens.
 * If a valid token is found in the Authorization header, the user information is extracted and
 * set in the SecurityContext, allowing for authentication in the Spring Security framework.
 * This filter extends OncePerRequestFilter, meaning it is executed only once per request.
 *
 * @author Vladislav Baryshev
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserServiceImpl customUserService;

    /**
     * This method is called during the request processing. It retrieves the JWT token from the request,
     * validates it, and if valid, sets the user details into the Spring Security context for the authenticated user.
     *
     * @param request the HTTP request object.
     * @param response the HTTP response object.
     * @param filterChain the filter chain to pass the request and response to the next filter.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtService.validateJwtToken(token)) {
            setCustomUserDetailsToSecurityContextHolder(token);
        }
        filterChain.doFilter(request, response);

    }

    /**
     * Sets the custom user details into the SecurityContextHolder based on the provided JWT token.
     * This ensures that the authenticated user is available for Spring Security.
     *
     * @param token the JWT token containing the user's email.
     */
    private void setCustomUserDetailsToSecurityContextHolder(String token) {
        String email = jwtService.getEmailFromToken(token);
        CustomUserDetails customUserDetails = customUserService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails,
                null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Retrieves the JWT token from the Authorization header of the HTTP request.
     * The token is expected to be prefixed with "Bearer ".
     *
     * @param request the HTTP request object.
     * @return the JWT token, or null if no token is found.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
