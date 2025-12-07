package com.example.login;
import com.example.login.model.User;
import com.example.login.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceRedisTest {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Test
//    void testCaching() {
//        // Create a new user
//        User user = new User("testUser");
//        User savedUser = userService.saveUser(user);
//
//        // Retrieve the user (should hit Redis if caching works)
//        User cachedUser = userService.getUser(savedUser.getId());
//        assertEquals("testUser", cachedUser.getName());
//    }
//
//    @Test
//    void testCachingWithDifferentUser() {
//        User user = new User("Safaa");
//        User savedUser = userService.saveUser(user);
//
//        User cachedUser = userService.getUser(savedUser.getId()); // Should hit Redis
//        assertEquals("Safaa", cachedUser.getName());
//    }
//
//    // Optional helper to check if user is in Redis or DB
//    private void checkUserInCacheOrDb(Long userId) {
//        String cacheKey = "users::" + userId;
//        Object cachedUser = redisTemplate.opsForValue().get(cacheKey);
//
//        if (cachedUser != null) {
//            System.out.println("User is in Redis cache: " + cachedUser);
//        } else {
//            System.out.println("User is NOT in Redis cache");
//        }
//    }
//
//    @Test
//    void testCheckUser() {
//        User user = new User("testUser2");
//        User savedUser = userService.saveUser(user);
//
//        // Check cache / DB
//        checkUserInCacheOrDb(savedUser.getId());
//    }
}
