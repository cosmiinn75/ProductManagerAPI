package com.example.spring_security.user;

import com.example.spring_security.config.JWTUtil;
import com.example.spring_security.dto.*;
import com.example.spring_security.exception.ExistingUsernameException;
import com.example.spring_security.exception.ForbiddenException;
import com.example.spring_security.exception.InvalidCredentialsException;
import com.example.spring_security.exception.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public List<UserResponse> getAllUsers() {
        checkAdminAccess();
        return userRepository.findAll()
                .stream()
                .map(this::fromUserToUserResponse)
                .toList();
    }

    public UserResponse changeUser(UserRequest userRequest, Long id) {
        checkAdminAccess();

        User wantedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!wantedUser.getUsername().equals(userRequest.username())
                && userRepository.findByUsername(userRequest.username()).isPresent()) {
            throw new ExistingUsernameException("Username already exists");
        }

        wantedUser.setUsername(userRequest.username());
        wantedUser.setName(userRequest.name());

        User savedUser = userRepository.save(wantedUser);

        return fromUserToUserResponse(savedUser);
    }

    public UserResponse changeRole(Long id , RoleRequest role ) {
        checkAdminAccess();

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        user.setRole(Role.valueOf(role.role()));
        userRepository.save(user);
        return fromUserToUserResponse(user);
    }


    private UserResponse fromUserToUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getRole().name()
        );
    }

    private String getCurrentUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User getCurrentUser(){
        return userRepository.findByUsername(getCurrentUsername()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
    }

    private void checkAdminAccess(){
        User currentUser = getCurrentUser();

        if(currentUser.getRole() != Role.ADMIN) {
            throw new ForbiddenException("You can't access this page");
        }
    }

}
