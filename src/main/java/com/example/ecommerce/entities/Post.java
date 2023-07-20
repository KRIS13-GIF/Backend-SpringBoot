package com.example.ecommerce.entities;

import com.example.ecommerce.enumerations.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.*;

import java.util.Date;

@Entity
@Data

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private boolean deleted;
    private String address;

    @ManyToOne
    private User user;

    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private Status status ;


}
