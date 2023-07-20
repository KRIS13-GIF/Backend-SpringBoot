package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.Post;
import com.example.ecommerce.enumerations.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, String> {

//Admin
    List<Post> findByTitleAndDescription(String title, String desc);

    List<Post> findByStatusAndDescription(Status status, String desc);
    List<Post> findByStatusAndTitle(Status status, String title);
    List<Post> findByTitleAndDescriptionAndStatus(String title, String desc, Status status);

    List<Post> findByTitle(String title);
    List<Post> findByDescription(String description);
    List<Post>findByStatus(Status status);



    //User
    List<Post> findAllByUser_Id(String id);

    List<Post>findAllByTitleAndDescriptionAndStatusAndUserId(String title, String desc, Status status, String id);
    List<Post>findAllByTitleAndUserId(String title, String id);
    List<Post>findAllByDescriptionAndUserId(String desc, String id);
    List<Post>findAllByStatusAndUserId(Status status, String id);

    List<Post>findAllByTitleAndDescriptionAndUserId(String title, String desc, String id);
    List<Post>findAllByTitleAndStatusAndUserId(String title, Status status, String id);
    List<Post>findAllByDescriptionAndStatusAndUserId(String desc, Status status, String id);

    //@Query(nativeQuery = true, value ="SELECT * FROM e-commerce.post ");





}
