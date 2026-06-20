package com.example.spring_security.user;

import com.example.spring_security.config.JWTUtil;
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

    public String loginUser(String username , String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid username");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())) {
         throw new RuntimeException("Incorrect password");
        }
    return jwtUtil.generateToken(user.getUsername(),user.getRole());
    }

    public User registerUser(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }


}
