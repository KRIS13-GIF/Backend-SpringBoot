package com.example.ecommerce.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ecommerce.entities.Post;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.enumerations.Role;
import com.example.ecommerce.enumerations.Status;
import com.example.ecommerce.models.PostRequest;
import com.example.ecommerce.models.PostResponse;
import com.example.ecommerce.repositories.PostRepo;
import com.example.ecommerce.services.PostService;
import com.example.ecommerce.services.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.xml.crypto.Data;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    UsersService usersService;

    @Mock
    private PostRepo postRepo;

    @InjectMocks
    PostService postService;


    @Test
    @DisplayName("This method creates a post")
    void testCreatePostSuccess() throws Exception {
        String userId = "some_user_id";
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Some title");
        postRequest.setDescription("Some description");
        User user = new User();
        user.setId(userId);
        user.setRole(Role.USER);
        when(usersService.findUser(userId)).thenReturn(user);
        when(postRepo.findAll()).thenReturn(new ArrayList<>());
        Post savedPost = new Post();
        savedPost.setId("some_post_id");
        when(postRepo.save(any(Post.class))).thenReturn(savedPost);
        PostResponse response = postService.createPost(postRequest, userId);
        assertEquals(savedPost.getId(), response.getId());
    }

    @Test
    @DisplayName("This method deletes a post")
    public void deletePost() throws Exception {
        String postId = "123";
        when(postRepo.findById(postId)).thenReturn(Optional.of(new Post()));
        postService.deletePost(postId);
    }

    @Test
    @DisplayName("This method takes one Post")
    public void testTakeOnePost_Success() {
        String postId = "post_1";
        Post mockPost = new Post();
        mockPost.setId(postId);
        mockPost.setTitle("Test Post");
        mockPost.setDescription("This is a test post");
        when(postRepo.findById(postId)).thenReturn(Optional.of(mockPost));
        Post resultPost = postService.takeOnePost(postId);
        verify(postRepo).findById(postId);
        assertEquals(mockPost, resultPost);
    }

    @Test
    @DisplayName("This method shows that the post is not found")
    public void testTakeOnePost_PostNotFound() {
        String postId = "post_1";
        when(postRepo.findById(postId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> postService.takeOnePost(postId));
        verify(postRepo).findById(postId);
    }


    @Test
    @DisplayName("This method shows all the posts done by the user")
    public void testFindAllByUser_Success() {
        String userId = "user_1";
        List<Post> mockPosts = new ArrayList<>();

        Post post1 = new Post();
        post1.setId("post_1");
        post1.setTitle("Post 1");
        post1.setDescription("This is post 1");
        post1.setUser(new User());
        mockPosts.add(post1);

        Post post2 = new Post();
        post2.setId("post_2");
        post2.setTitle("Post 2");
        post2.setDescription("This is post 2");
        post2.setUser(new User());
        mockPosts.add(post2);
        when(postRepo.findAllByUser_Id(userId)).thenReturn(mockPosts);
        List<Post> resultPosts = postService.findAllByUser(userId);
        verify(postRepo).findAllByUser_Id(userId);
        assertEquals(mockPosts, resultPosts);
    }

    @Test
    @DisplayName("This method shows that no posts are found when a user does not exist")
    public void testFindAllByUser_UserNotFound() {
        String userId = "user_1";
        when(postRepo.findAllByUser_Id(userId)).thenReturn(new ArrayList<>());
        List<Post> resultPosts = postService.findAllByUser(userId);
        verify(postRepo).findAllByUser_Id(userId);
        assertTrue(resultPosts.isEmpty());
    }









}











