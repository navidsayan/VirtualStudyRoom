package com.example.virtualstudyroom.controller;

import com.example.virtualstudyroom.model.User;
import com.example.virtualstudyroom.model.UserResponse;
import com.example.virtualstudyroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public UserResponse addNewUser(@RequestBody User user){

        return userService.addNewUser(user);
    }
}
