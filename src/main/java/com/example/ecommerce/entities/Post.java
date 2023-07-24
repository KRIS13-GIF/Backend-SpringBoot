package com.example.ecommerce.entities;

import com.example.ecommerce.enumerations.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.*;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private boolean deleted;
    private String address;
    private String name;
    private String type;


    @ManyToOne
    private User user;

    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private Status status ;

    @Lob
    @Column(name = "image", length = 1000000)
    private byte[] image;


}
