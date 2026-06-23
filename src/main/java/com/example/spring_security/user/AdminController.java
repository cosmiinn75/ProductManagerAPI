package com.example.spring_security.user;

import com.example.spring_security.dto.RoleRequest;
import com.example.spring_security.dto.UserRequest;
import com.example.spring_security.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {


    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserResponse changeUser(@Valid @RequestBody UserRequest userRequest , @PathVariable Long id){
       return userService.changeUser(userRequest,id);
    }

    @PutMapping("/{id}/role")
    public UserResponse changeRole(@PathVariable Long id , @RequestBody RoleRequest role){
     return userService.changeRole(id,role);
    }

}
