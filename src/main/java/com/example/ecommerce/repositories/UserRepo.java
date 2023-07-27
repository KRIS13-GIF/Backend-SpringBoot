package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    List<User> findAll();

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);


}
