package org.vladislavb.onemediatesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vladislavb.onemediatesttask.dto.JwtAuthenticationDto;
import org.vladislavb.onemediatesttask.dto.RefreshTokenDto;
import org.vladislavb.onemediatesttask.dto.UserCredentialsDto;
import org.vladislavb.onemediatesttask.service.UserService;

import javax.naming.AuthenticationException;

@Tag(name = "Authentication", description = "Endpoints for authentication and authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Sign In", description = "Authenticate user and return JWT token.")
    @ApiResponse(responseCode = "200", description = "Successfully authenticated")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/sing-in")
    public ResponseEntity<JwtAuthenticationDto> singIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @Operation(summary = "Refresh Token", description = "Refresh JWT token using a refresh token.")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid refresh token")
    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }

    @Operation(summary = "Logout", description = "Invalidate refresh token and log out the user.")
    @ApiResponse(responseCode = "200", description = "Logged out successfully")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        userService.logout(refreshTokenDto);
        return ResponseEntity.ok("Logged out successfully");
    }
}
