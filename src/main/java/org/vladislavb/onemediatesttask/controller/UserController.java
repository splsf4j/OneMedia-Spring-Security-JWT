package org.vladislavb.onemediatesttask.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import org.vladislavb.onemediatesttask.dto.UserDto;
import org.vladislavb.onemediatesttask.service.UserService;

@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register User", description = "Registers a new user and returns a confirmation message.")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/registration")
    public String createUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @Operation(summary = "Get User by ID", description = "Retrieves user details by user ID.")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return userService.getUserById(id);
    }

    @Operation(summary = "Get User by Email", description = "Retrieves user details by email.")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        return userService.getUserByEmail(email);
    }
}
