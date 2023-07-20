package com.example.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
