package com.example.redis_demo;
import com.example.redis_demo.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.GenericContainer;

import com.example.redis_demo.model.User;
import com.example.redis_demo.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceRedisTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.0.12")
            .withExposedPorts(6379);

    static {
        redisContainer.start();
        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
    }

    @Test
    void testCaching() {
        User user = new User("testUser");
// Don't set ID manually
        User savedUser = userService.saveUser(user);

        User cachedUser = userService.getUser(savedUser.getId());
        assertEquals("testUser", cachedUser.getName());
    }

    @Test
    void testCaching1() {
        User user = new User("Safaa");
        user.setId(3L);

        userService.saveUser(user); // Cache put

        User cachedUser = userService.getUser(3L); // Should hit Redis
        assertEquals("Safaa", cachedUser.getName());
    }
    // --- Add the helper method here ---
    private void checkUserInCacheOrDb(Long userId) {
        String cacheKey = "users::" + userId;
        Object cachedUser = redisTemplate.opsForValue().get(cacheKey);

        if (cachedUser != null) {
            System.out.println("User is in Redis cache: " + cachedUser);
        } else {
            System.out.println("User is NOT in Redis cache");
        }

        userRepository.findById(userId).ifPresentOrElse(
                user -> System.out.println("User is in DB: " + user),
                () -> System.out.println("User is NOT in DB")
        );
    }
    @Test
    void testCheckUser() {
        User user = new User("testUser");
        User savedUser = userService.saveUser(user);

        // Check if the user is in cache or DB
        checkUserInCacheOrDb(savedUser.getId());
    }
}
