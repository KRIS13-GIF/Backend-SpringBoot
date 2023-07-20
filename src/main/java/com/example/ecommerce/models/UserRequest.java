package com.example.ecommerce.models;

import com.example.ecommerce.enumerations.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;



}
