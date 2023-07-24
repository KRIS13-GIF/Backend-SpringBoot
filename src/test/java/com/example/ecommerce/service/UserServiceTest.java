package com.example.ecommerce.service;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.enumerations.Role;
import com.example.ecommerce.models.UserRequest;
import com.example.ecommerce.models.UserResponse;
import com.example.ecommerce.repositories.UserRepo;
import com.example.ecommerce.services.UsersService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    UsersService usersService;


    @Test
    @DisplayName("This method deletes a user")
    public void deleteUser() throws Exception {
        String userId = "123";
        when(userRepo.findById(userId)).thenReturn(Optional.of(new User()));
        usersService.deleteUser(userId);
    }

    @Test
    public void testCreateUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Test");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");
        userRequest.setAddress("test");
        userRequest.setRole(Role.USER);
        Mockito.when(userRepo.findAll()).thenReturn(new ArrayList<>()); // No existing users
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId("userId"); // Set a dummy ID
            return user;
        });
        UserResponse response = usersService.createUser(userRequest);
        Assertions.assertNotNull(response.getId());
        verify(userRepo, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void DuplicateUsername() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("existingUser");
        userRequest.setPassword("password");
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        existingUser.setPassword("password");
        List<User> userList = new ArrayList<>();
        userList.add(existingUser);
        when(userRepo.findAll()).thenReturn(userList);
        Assertions.assertThrows(Exception.class, () -> usersService.createUser(userRequest));
        verify(userRepo, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    public void DuplicatePassword() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setPassword("existingPassword");
        User existingUser = new User();
        existingUser.setUsername("anotherUser");
        existingUser.setPassword("existingPassword");
        List<User> userList = new ArrayList<>();
        userList.add(existingUser);
        when(userRepo.findAll()).thenReturn(userList);
        Assertions.assertThrows(Exception.class, () -> usersService.createUser(userRequest));
        verify(userRepo, Mockito.never()).save(Mockito.any(User.class));
    }







}

