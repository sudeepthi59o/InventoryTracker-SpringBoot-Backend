package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.error.UsernameExistsException;
import com.sr.inventory_tracker.DTO.TokenDTO;
import com.sr.inventory_tracker.DTO.UserDTO;
import com.sr.inventory_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserDTO userDTO) throws UsernameExistsException {
        log.info("Inside register method in controller");
        return userService.register(userDTO);
    }

    @Operation(summary = "Login a user")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully and token returned")
    @ApiResponse(responseCode = "400", description = "Invalid credentials")
    @PostMapping("/login")
    public TokenDTO login(@Valid @RequestBody UserDTO userDTO) {
        log.info("Inside login method in controller");
        return userService.verify(userDTO);
    }
}
