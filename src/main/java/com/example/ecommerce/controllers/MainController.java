package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.Favourites;
import com.example.ecommerce.entities.Post;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.models.*;
import com.example.ecommerce.services.FavServices;
import com.example.ecommerce.services.PostService;
import com.example.ecommerce.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/program")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {


    private final UsersService usersService;
    private final PostService postService;
    private final FavServices favServices;



@Autowired
    public MainController(UsersService usersService, @Lazy PostService postService, FavServices favServices) {
        this.usersService = usersService;
        this.postService = postService;
        this.favServices = favServices;

    }


    @PostMapping("/create/user")
    public ResponseEntity <UserResponse>createUser(
            @RequestBody UserRequest userRequest
    ) throws Exception {
        UserResponse response=usersService.createUser(userRequest);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @PutMapping("/softDelete/{id}")
    public void softDelete(
            @PathVariable String id) throws Exception{
        usersService.deleteUserById(id);

    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(
            @PathVariable String id
    )throws Exception{
        return usersService.getUserId(id);
    }


    @PutMapping("/update/user/{id}")
    public ResponseEntity<UserResponse>updateUser(
            @RequestBody UserRequest userRequest,
            @PathVariable String id
    ) throws Exception{
        UserResponse response=usersService.updateUser(userRequest, id);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity deleteUser(
            @PathVariable(name = "id")String id)throws Exception{
        usersService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/showPosts")
    public List<Post> showPosts(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size) {
        return postService.showAllPosts(page, size);
    }

    @PostMapping("/create/post/{id}")
    public ResponseEntity<PostResponse>createProduct(
            @RequestBody PostRequest postRequest,
            @PathVariable String id
    )throws Exception{
        return new ResponseEntity(postService.createPost(postRequest, id), HttpStatus.OK);
    }
    @PatchMapping("/update/post/{id}")
    public ResponseEntity<PostResponse>updateProduct(
            @RequestBody PostRequest postRequest,
            @PathVariable String id
    )throws Exception{
        PostResponse response=postService.updatePost(postRequest,id);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/delete/post/{id}")
    public ResponseEntity<PostResponse>deletePost(
            @PathVariable String id
    )throws Exception{
        postService.deletePost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{admin}/{id}")
    public ResponseEntity<PostResponse>changeStatus(
            @PathVariable String admin,
            @PathVariable String id
    )throws Exception{

        PostResponse response=postService.changeStatus(admin, id);
        return new ResponseEntity(response, HttpStatus.OK);

    }

    @GetMapping("/postId/{id}")
    public Post getPost(
            @PathVariable String id
    )throws Exception{
        return postService.takeOnePost(id);
    }

    @PostMapping("/find/post")
    public List<Post> findPosts(
            @RequestBody PostReq2 postReq2
    )throws Exception{
        List<Post> post=  postService.findPost(postReq2);
        return (post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable String id){
        byte[] imageData=postService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @PostMapping("find/postUser/{id}")
    public List<Post>findPostByUser(
            @PathVariable(name = "id")String id,
            @RequestBody PostReq2 postReq2)throws Exception{
        List<Post>posts=postService.findPostsByUser(postReq2, id);
        return posts;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String>uploadImage(
            @PathVariable(name = "id")String id,
            @RequestParam("image")MultipartFile multipartFile
    ) throws IOException {
        String res= postService.uploadImage(id, multipartFile);
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }






    @GetMapping("/post/{userId}")
    public List<Post> findPostsById(
            @PathVariable String userId
    )throws Exception{
        return postService.findAllByUser(userId);
    }


    @GetMapping("/getImage/{id}")
    public ResponseEntity<String> getImage(@PathVariable(name = "id") String id) {
        try {
            byte[] imageData = postService.getImage(id);
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            return ResponseEntity.ok().body(base64Image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching image: " + e.getMessage());
        }
    }


    @PostMapping("/addFav/{id}")
    public FavResponse addFavourite(
            @PathVariable String id
    )throws Exception{
        return favServices.addFavorite(id);
    }

    @DeleteMapping("/deleteFav/{id}")
    public ResponseEntity deleteFav(
            @PathVariable(name = "id")String id
    )throws Exception{
        favServices.deleteFromFav(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("deleteHardPost/{id}")
    public ResponseEntity deletePostHard(
            @PathVariable(name = "id")String id
    )throws Exception{
        postService.hardDelete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/allFav")
    public List<Favourites>showAllFav(){
        return favServices.showAllFav();
    }


    @GetMapping("/showAllUsers")
    public List<User>showAllUsers(){
        return usersService.findAllUsers();
    }

    @GetMapping("/fav/{id}")
    public List<Favourites>findByUserId(
            @PathVariable(name = "id")String id
    )throws Exception{
        return favServices.showAllFavByUserId(id);
    }



}