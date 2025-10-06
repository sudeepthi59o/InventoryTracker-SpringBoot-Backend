package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.error.UsernameExistsException;
import com.sr.inventory_tracker.model.UserDTO;
import com.sr.inventory_tracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserDTO userDTO) throws UsernameExistsException {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserDTO userDTO) {
        return userService.verify(userDTO);
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrf(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
