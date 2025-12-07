package com.example.login.controller;

import com.example.login.model.User;
import com.example.login.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }
    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}/cache")
    public String deleteUserCache(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Cache cleared for user " + id;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        userService.updateUser(id, user); // updates DB and evicts cache
        return "User " + id + " updated";
    }

    @GetMapping()
    public List<User> getAllUsers() {
    return    userService.getUsers();}

}
