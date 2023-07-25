package com.example.ecommerce.services;


import com.example.ecommerce.entities.Post;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enumerations.Role;
import com.example.ecommerce.enumerations.Status;
import com.example.ecommerce.models.PostReq2;
import com.example.ecommerce.models.PostRequest;
import com.example.ecommerce.models.PostResponse;
import com.example.ecommerce.repositories.PostRepo;

import com.example.ecommerce.util.ImageUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class PostService {

    private final PostRepo postRepo;
    private final UsersService usersService;

    public PostService(PostRepo postRepo, UsersService usersService) {
        this.postRepo = postRepo;
        this.usersService = usersService;
    }

    public PostResponse createPost(PostRequest postRequest, String id) throws Exception {
        User user = usersService.findUser(id);
        List<Post>postList=postRepo.findAll();
        if (user.getRole() == Role.USER) {
            Post post = new Post();
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setAddress(user.getAddress());
            post.setStatus(Status.PENDING);
            post.setDeleted(false);
            post.setCreatedAt(Date.valueOf(LocalDate.now()));
            post.setUser(user);
            return new PostResponse(postRepo.save(post).getId());
        }
        throw new Exception("You need to be a user");
    }

    public String uploadImage(String postId, MultipartFile multipartFile) throws IOException {
        Optional<Post>postOptional=postRepo.findById(postId);
        if (postOptional.isEmpty()){
            throw new EntityNotFoundException("Post does not exist");
        }
        Post post=postOptional.get();
        post.setName(multipartFile.getOriginalFilename());
        post.setType(multipartFile.getContentType());
        post.setImage(ImageUtil.compressImage(multipartFile.getBytes()));
        postRepo.save(post);

        return "FIle uploaded ";
    }

    public byte[] downloadImage(String id){
        Optional<Post> dbImageData = postRepo.findById(id);
        byte[] images=ImageUtil.decompressImage(dbImageData.get().getImage());
        return images;
    }

    public byte[] getImage(String id){
        return postRepo.findById(id).get().getImage();
    }



    public PostResponse updatePost(PostRequest postRequest, String id) throws Exception {
        // List<String> stringList = new ArrayList<>();
//        Optional<Post> posts = postRepo.findById(postRequest.getId());

        Optional<Post> post = postRepo.findById(id);
        if (post.isEmpty()) {
            throw new Exception("Post does not exist");
        }

        Post optionalPost = post.get();// e merr optional si objekt.
        if (optionalPost.getStatus() == Status.PENDING) {
            if (postRequest.getTitle() == "" || postRequest.getDescription() == "" || postRequest.getAddress()==" ") {
                throw new Exception("No blank values are allowed");

            }
            else{
                optionalPost.setTitle(postRequest.getTitle());
                optionalPost.setDescription(postRequest.getDescription());
                optionalPost.setAddress(postRequest.getAddress());
            }



        }
        Post savedPost = postRepo.save(optionalPost);
        PostResponse response = new PostResponse(
                savedPost.getId()
        );
        return response;
    }


    public void deletePost(String id) throws Exception {
        Optional<Post> post = postRepo.findById(id);
        if (post.isEmpty()) {
            throw new Exception("Id does not exist");
        }
        Post post1 = post.get();
        post1.setDeleted(true);
        postRepo.save(post1);
    }

    public PostResponse changeStatus(String idAdmin, String idProduct) throws Exception {
        User user = usersService.findUser(idAdmin);
        if (user.getRole() == Role.ADMIN) {
            Optional<Post> post = postRepo.findById(idProduct);
            if (post.isEmpty()) {
                throw new Exception("This product does not exist");
            }
            Post post1 = post.get();
            if (post1.getStatus()==Status.APPROVED){
                throw new Exception("This status is already approved!");
            }
            post1.setStatus(Status.APPROVED);
            Post savedPost = postRepo.save(post1);
            PostResponse response = new PostResponse(
                    savedPost.getId()
            );
            return response;

        } else {
            throw new Exception("You are not an admin");
        }


    }
    public Post takeOnePost(String id){
        return postRepo.findById(id).get();
    }



    public List<Post> showAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepo.findAll(pageable);
        return postPage.getContent();
    }


    public Post findPostById(String id) {
        return postRepo.findById(id).get();
    }







    public List<Post>findAllByUser(String id){
        List<Post> postlist=postRepo.findAllByUser_Id(id);
        return postlist;
    }


    public List<Post>findPostsByUser(PostReq2 postReq2, String id) {

        User user = usersService.findUser(id);
        if (postReq2.getTitle() != "" && postReq2.getDescription() == "" && postReq2.getStatus() != null) {
            return postRepo.findAllByTitleAndDescriptionAndStatusAndUserId(postReq2.getTitle(), postReq2.getDescription(), postReq2.getStatus(), user.getId());
        } else if (postReq2.getTitle()!="") {
            return postRepo.findAllByTitleAndUserId(postReq2.getTitle(), user.getId());
        }
        else if (postReq2.getDescription()!=""){
            return postRepo.findAllByDescriptionAndUserId(postReq2.getDescription(), user.getId());
        } else if (postReq2.getStatus()!=null) {
            return postRepo.findAllByStatusAndUserId(postReq2.getStatus(), user.getId());
        } else if (postReq2.getTitle()!="" && postReq2.getDescription()!="") {
            return postRepo.findAllByTitleAndDescriptionAndUserId(postReq2.getTitle(), postReq2.getDescription(), user.getId());
        } else if (postReq2.getTitle()!="" && postReq2.getStatus()!=null) {
            return postRepo.findAllByTitleAndStatusAndUserId(postReq2.getTitle(), postReq2.getStatus(), user.getId());
        } else if (postReq2.getDescription()!=""&& postReq2.getStatus()!=null) {
            return postRepo.findAllByDescriptionAndStatusAndUserId(postReq2.getDescription(), postReq2.getStatus(), user.getId());
        }
        else if(postReq2.getTitle()=="" && postReq2.getDescription()=="" && postReq2.getStatus()==null){
            return postRepo.findAllByUser_Id(user.getId());
        }
        else {
            return null;
        }
    }

    public void hardDelete(String id) throws Exception {
        if (postRepo.findById(id).isEmpty()){
            throw new Exception("Id does not exist");
        }
        postRepo.deleteById(id);
    }



    public List<Post> findPost(PostReq2 postReq2) {


        if(postReq2.getDescription()!=""&&postReq2.getTitle()!=""&&postReq2.getStatus()!=null){
            return postRepo.findByTitleAndDescriptionAndStatus(postReq2.getTitle(), postReq2.getDescription(), postReq2.getStatus());
        }
        else if(postReq2.getDescription()!=""&&postReq2.getTitle()!=""){
            return postRepo.findByTitleAndDescription(postReq2.getTitle(), postReq2.getDescription());
        }
        else if(postReq2.getStatus()!=null &&postReq2.getTitle()!=""){
            return  postRepo.findByStatusAndTitle(postReq2.getStatus(), postReq2.getTitle());

        }
       else  if(postReq2.getDescription()!=""&&postReq2.getStatus()!=null){
           return postRepo.findByStatusAndDescription(postReq2.getStatus(), postReq2.getDescription());
        }

       else if (postReq2.getTitle()!=""){
           return postRepo.findByTitle(postReq2.getTitle());
        }

        else if (postReq2.getDescription()!=""){
            return postRepo.findByDescription(postReq2.getDescription());
        }
        else if (postReq2.getStatus()!=null){
            return postRepo.findByStatus(postReq2.getStatus());
        }
        else if(postReq2.getTitle()=="" && postReq2.getDescription()=="" && postReq2.getStatus()==null){
            return postRepo.findAll();
        }
        else{
            return null;
        }

    }
    public Post findPostId(String id) {
        return postRepo.findById(id).get();


    }
}


