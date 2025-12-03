package com.example.redis_demo.services;

import com.example.redis_demo.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<Long, User> fakeDatabase = new HashMap<>();

    public UserService() {
        fakeDatabase.put(1L, new User(1L, "Ahmed"));
        fakeDatabase.put(2L, new User(2L, "Sara"));
    }

    @Cacheable(value = "users", key = "#id")
    public User getUser(Long id) {
        System.out.println("Fetching from DB...");
        return fakeDatabase.get(id);
    }
    // Evict cache for a specific user
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        System.out.println("Cache cleared for user " + id);
    }

    // Example: updating a user automatically clears cache
    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, String newName) {
        // Update database here
        System.out.println("User " + id + " updated in DB to " + newName);
        // Cache automatically cleared
    }


}
