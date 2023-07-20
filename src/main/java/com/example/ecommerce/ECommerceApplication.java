package com.example.ecommerce;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.enumerations.Role;
import com.example.ecommerce.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;

@SpringBootApplication

public class ECommerceApplication implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;
    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*
        User user=new User();
        user.setUsername("KRIS_BEKA");
        user.setFirstName("KRIS");
        user.setLastName("BEKA");
        user.setEmail("krisbeka@esc.edu");
        user.setDeleted(false);
        user.setAddress("DURRES");
        user.setCreatedAt(Date.valueOf(LocalDate.now()));
        user.setPassword("krisbeka123");
        user.setRole(Role.ADMIN);
        userRepo.save(user);
        System.out.println("Admin added");
*/
    }
}
