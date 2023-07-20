package com.example.ecommerce.repositories;


import com.example.ecommerce.entities.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavRepo extends JpaRepository<Favourites, String> {

    List<Favourites>findAllByUser_Id(String id);
}
