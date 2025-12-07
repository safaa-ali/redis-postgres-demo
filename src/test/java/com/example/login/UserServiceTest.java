package com.example.login;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.login.dao.UserRepository;
import com.example.login.model.User;
import com.example.login.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser_FromDB() {
        User user = new User("Safaa");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertEquals("Safaa", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveUser() {
        User user = new User("Ali");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals("Ali", result.getName());
        verify(userRepository, times(1)).save(user);
    }
}

