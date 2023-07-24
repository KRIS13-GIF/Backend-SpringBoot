package com.example.ecommerce.entities;
import com.example.ecommerce.enumerations.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile; /**
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;*/

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
        //implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // here is the change for overriding the password
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Date createdAt;
    private Boolean deleted;


}
