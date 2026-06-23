package com.example.spring_security.user;

import com.example.spring_security.config.JWTUtil;
import com.example.spring_security.dto.AuthResponse;
import com.example.spring_security.dto.RegisterRequest;
import com.example.spring_security.exception.ExistingUsernameException;
import com.example.spring_security.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse loginUser(String username , String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())) {
         throw new InvalidCredentialsException("Invalid username or password");
        }
    return new AuthResponse(jwtUtil.generateToken(user.getUsername(),user.getRole()));
    }

    public User registerUser(RegisterRequest registerRequest) {
        User savedUser = new User();
        if(userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new ExistingUsernameException("Username already exists");
        }
        savedUser.setName(registerRequest.name());
        savedUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        savedUser.setUsername(registerRequest.username());
        savedUser.setRole(Role.CUSTOMER);
        return userRepository.save(savedUser);
    }


}
