package com.example.spring_security.user;


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
    public User registerUser(@Valid @RequestBody User user) {

        return userService.registerUser(user);

    }
    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody User user) {
    return userService.loginUser(user.getUsername(),user.getPassword());
    }

}
