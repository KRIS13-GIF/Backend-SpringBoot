package com.example.ecommerce.models;

import com.example.ecommerce.enumerations.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class PostRequest {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;

    private String address;
    @Enumerated(EnumType.STRING)
    private Status status;

    private Date createdAt;
   // private String userId;

}
