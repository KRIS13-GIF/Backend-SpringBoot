package com.example.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.ecommerce.entities.Favourites;
import com.example.ecommerce.repositories.FavRepo;
import com.example.ecommerce.services.FavServices;
import com.example.ecommerce.services.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FavServiceTest {

    @Mock
    private PostService postService;

    @Mock
    private FavRepo favRepo;

    @InjectMocks
    private FavServices favServices;

    @Test
    @DisplayName("Delete from fav")
    void testDeleteFromFavSuccess() throws Exception {
        String favId = "some_fav_id";
        Favourites fav = new Favourites();
        fav.setId(favId);
        when(favRepo.findById(favId)).thenReturn(Optional.of(fav));
        favServices.deleteFromFav(favId);
        verify(favRepo, times(1)).deleteById(favId);
    }

    @Test
    void testShowAllFav() {
        List<Favourites> mockFavs = List.of(new Favourites(), new Favourites());
        when(favRepo.findAll()).thenReturn(mockFavs);
        List<Favourites> result = favServices.showAllFav();
        assertEquals(mockFavs, result);
    }

    @Test
    void testShowAllFavByUserId() {
        String userId = "some_user_id";
        List<Favourites> mockFavs = List.of(new Favourites(), new Favourites());
        when(favRepo.findAllByUser_Id(userId)).thenReturn(mockFavs);
        List<Favourites> result = favServices.showAllFavByUserId(userId);
        assertEquals(mockFavs, result);
    }


}
