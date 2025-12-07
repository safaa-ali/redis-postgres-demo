package com.example.login.services;

import com.example.login.dao.UserRepository;
import com.example.login.exception.ResourceNotFoundException;
import com.example.login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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

       return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    }

    @CachePut(value = "users", key = "#result.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));


        userRepository.deleteById(user.getId());
    }
    // Example: updating a user automatically clears cache
    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);

    }
}

