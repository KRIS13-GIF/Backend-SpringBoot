package com.example.ecommerce.services;

import com.example.ecommerce.entities.Post;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enumerations.Role;
import com.example.ecommerce.models.UserRequest;
import com.example.ecommerce.models.UserResponse;
import com.example.ecommerce.repositories.UserRepo;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {
    private final UserRepo userRepo;


    public UsersService(UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    public UserResponse createUser(UserRequest userRequest) throws Exception {
        List<User> userList = userRepo.findAll();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(userRequest.getUsername())) {
                throw new Exception("Are not allowed two exact usernames");
            } else if (userList.get(i).getPassword().equals(userRequest.getPassword())) {
                throw new Exception("Are not allowed two exact passwords");
            }
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());




        user.setPassword(userRequest.getPassword());
        user.setAddress(userRequest.getAddress());
        if (userRequest.getRole() == Role.ADMIN) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        user.setCreatedAt(Date.valueOf(LocalDate.now()));
        user.setDeleted(false);

        User savedUser = userRepo.save(user);
        UserResponse response = new UserResponse(savedUser.getId());
        return response;
    }

    public Optional<User> getUserId(String id) {
        Optional<User> user = userRepo.findById(id);
        return user;
    }

    public void deleteUserById(String id) {
        Optional<User> user = userRepo.findById(id);
        User user1 = user.get();
        user1.setDeleted(true);
        userRepo.save(user1);
    }

    public UserResponse updateUser(UserRequest userRequest, String id) throws Exception {
        Optional<User> users = userRepo.findById(id);


        if (users.isEmpty()) {
            throw new Exception("Product does not exist");
        }

        if (userRequest.getUsername()=="" || userRequest.getFirstName()==""
        || userRequest.getLastName()=="" || userRequest.getEmail()=="" || userRequest.getAddress()==""){
            throw new Exception("No blank fields allowed!");
        }

        User user = users.get();
        if (userRequest.getUsername() != null) {
            List<User>userList=userRepo.findAll();
            for(int i=0; i<userList.size(); i++){
                if (userList.get(i).getUsername().equals(userRequest.getUsername())){
                    throw new Exception("This username already exists. No update allowed");
                }
            }
            user.setUsername(userRequest.getUsername());
        }
        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getAddress() != null) {
            user.setAddress(userRequest.getAddress());
        }
        if (userRequest.getRole() != null) {
            user.setRole(userRequest.getRole());
        }
        user.setCreatedAt(Date.valueOf(LocalDate.now()));
        user.setDeleted(false);
        User savedUser = userRepo.save(user);
        UserResponse response = new UserResponse(savedUser.getId());
        return response;
    }

    public void deleteUser(String id) throws Exception {
        if (userRepo.findById(id).isEmpty()) {
            throw new Exception("Id does not exist");
        }
        userRepo.deleteById(id);
    }

    public List<User> findAllUsers() {
        List<User> userList = userRepo.findAll();
        List<User> userRoleList = userList.stream()
                .filter(user -> user.getRole() == Role.USER)
                .collect(Collectors.toList());
        return userRoleList;
    }

    public User findUser(String id) {
        return userRepo.findById(id).get();
    }
}
