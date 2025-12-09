package com.example.login.services;

import com.example.login.dao.UserRepository;
import com.example.login.exception.ResourceNotFoundException;
import com.example.login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


@Autowired
     private UserRepository userRepository;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "usersList")
    public List<User> getUsers() {
        System.out.println("Fetching from DB...");
        return userRepository.findAll();
    }
    @Cacheable(value = "userById", key = "#id")
    public User getUser(Long id) {
       return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    }

    @CachePut(value = "userById", key = "#result.id")
    public User saveUser(User user) {
        User saved = userRepository.save(user);
        updateUsersListCache(saved, "ADD");
        return saved;
    }

    @CachePut(value = "userById", key = "#id")
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());
        User saved = userRepository.save(user);
        updateUsersListCache(saved, "UPDATE");
        return saved;
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
        updateUsersListCache(user, "DELETE");
    }



    private void updateUsersListCache(User user, String action) {
        var cache = cacheManager.getCache("usersList");
        if (cache == null) return;

        List<User> users = cache.get(SimpleKey.EMPTY, List.class);
        if (users == null) return;  // no list in cache yet, will be cached next GET

        switch(action) {
            case "ADD":
                users.add(user);
                break;
            case "UPDATE":
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId().equals(user.getId())) {
                        users.set(i, user);
                        break;
                    }
                }
                break;
            case "DELETE":
                users.removeIf(u -> u.getId().equals(user.getId()));
                break;
        }

        cache.put(SimpleKey.EMPTY, users);
    }


}

