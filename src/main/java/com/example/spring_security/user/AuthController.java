package com.example.spring_security.user;


import com.example.spring_security.dto.AuthRequest;
import com.example.spring_security.dto.AuthResponse;
import com.example.spring_security.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }
    @PostMapping("/login")
    public AuthResponse loginUser(@Valid @RequestBody AuthRequest authRequest) {
    return userService.loginUser(authRequest.username(),authRequest.password());
    }

}
