package com.example.redis_demo.services;

import com.example.redis_demo.dao.UserRepository;
import com.example.redis_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Cacheable(value = "users")
    public List<User> getUsers() {
        System.out.println("Fetching from DB...");
        return userRepository.findAll();
    }
    @Cacheable(value = "users", key = "#id")
    public User getUser(Long id) {
        System.out.println("Fetching from DB...");
        return userRepository.findById(id).orElse(null);
    }



    @CachePut(value = "users", key = "#result.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.deleteById(user.getId());
    }
    // Example: updating a user automatically clears cache
    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, String newName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(newName);
        userRepository.save(user);

    }
}

