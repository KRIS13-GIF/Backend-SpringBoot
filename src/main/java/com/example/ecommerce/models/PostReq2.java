package com.example.ecommerce.models;

import com.example.ecommerce.enumerations.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class PostReq2 {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

}
