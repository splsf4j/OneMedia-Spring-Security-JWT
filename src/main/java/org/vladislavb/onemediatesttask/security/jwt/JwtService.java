package org.vladislavb.onemediatesttask.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vladislavb.onemediatesttask.dto.JwtAuthenticationDto;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * JwtService is a service responsible for generating, validating, and refreshing JWT tokens.
 * It provides methods to create both authentication and refresh tokens, as well as to invalidate tokens.
 * The service uses the JJWT library to generate and validate the tokens.
 *
 * @author Vladislav Baryshev
 */
@Component
public class JwtService {

    private static final Logger LOGGER = LogManager.getLogger(JwtService.class);
    @Value("8074658237c236e39e96e909ac1abb25a3e1773b100096ad6877c439cd452c17")
    private String jwtSecret;
    private final Set<String> invalidatedTokens = new HashSet<>();

    /**
     * Generates a pair of authentication and refresh tokens for the given email address.
     *
     * @param email the email address to associate with the generated tokens.
     * @return a JwtAuthenticationDto containing the generated authentication and refresh tokens.
     */
    public JwtAuthenticationDto generateAuthToken(String email) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(email));
        jwtDto.setRefreshToken(generateRefreshToken(email));
        return jwtDto;
    }

    /**
     * Invalidates the specified token by adding it to the list of invalidated tokens.
     *
     * @param token the JWT token to invalidate.
     */
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    /**
     * Checks if the provided token has been invalidated.
     *
     * @param token the JWT token to check.
     * @return true if the token is invalidated, false otherwise.
     */
    public boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }

    /**
     * Refreshes the base authentication token using the provided refresh token and email address.
     *
     * @param email the email address associated with the token.
     * @param refreshToken the refresh token used to generate a new authentication token.
     * @return a JwtAuthenticationDto containing the new authentication token and the provided refresh token.
     */
    public JwtAuthenticationDto refreshBaseToken(String email, String refreshToken) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(email));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    /**
     * Extracts the email address from the given JWT token.
     *
     * @param token the JWT token to extract the email from.
     * @return the email address encoded in the token.
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Validates the provided JWT token by checking its structure and signature.
     *
     * @param token the JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch (ExpiredJwtException expEx){
            LOGGER.error("Expired JwtException", expEx);
        }catch (UnsupportedJwtException expEx){
            LOGGER.error("Unsupported JwtException", expEx);
        }catch (MalformedJwtException expEx){
            LOGGER.error("Malformed JwtException", expEx);
        }catch (SecurityException expEx){
            LOGGER.error("Security Exception", expEx);
        }catch (Exception expEx){
            LOGGER.error("invalid token", expEx);
        }
        return false;
    }

    /**
     * Generates a JWT authentication token for the given email address.
     * The token will expire in 1 minute.
     *
     * @param email the email address to associate with the JWT token.
     * @return the generated JWT authentication token.
     */
    private String generateJwtToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    /**
     * Generates a JWT refresh token for the given email address.
     * The refresh token will expire in 1 day.
     *
     * @param email the email address to associate with the refresh token.
     * @return the generated JWT refresh token.
     */
    private String generateRefreshToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    /**
     * Retrieves the signing key used for generating and validating JWT tokens.
     *
     * @return the SecretKey used for signing JWT tokens.
     */
    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
