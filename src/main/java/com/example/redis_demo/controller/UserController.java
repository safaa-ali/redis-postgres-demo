package com.example.redis_demo.controller;

import com.example.redis_demo.model.User;
import com.example.redis_demo.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public String updateUser(@PathVariable Long id, @RequestParam String name) {
        userService.updateUser(id, name); // updates DB and evicts cache
        return "User " + id + " updated";
    }
}
