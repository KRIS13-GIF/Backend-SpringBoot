package com.example.ecommerce.services;

import com.example.ecommerce.entities.Favourites;
import com.example.ecommerce.entities.Post;
import com.example.ecommerce.models.FavResponse;
import com.example.ecommerce.repositories.FavRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FavServices {

    private final FavRepo favRepo;
    private final PostService postService;

    private final UsersService usersService;

    public FavServices(FavRepo favRepo, PostService postService, UsersService usersService) {
        this.favRepo = favRepo;
        this.postService = postService;
        this.usersService = usersService;
    }

    public FavResponse addFavorite(String id) throws Exception {
        Post post=postService.takeOnePost(id);
        List <Favourites>favouritesList=favRepo.findAll();

        //Checking if you click two times the same post
        for(int i=0; i<favouritesList.size(); i++){
            if (favouritesList.get(i).getPost().getId()==post.getId()){
                throw new Exception("No duplicate Posts");
            }
        }
        Favourites favourites=new Favourites();
        favourites.setUser(post.getUser());
        favourites.setPost(post);
        Favourites fav=favRepo.save(favourites);
        FavResponse response=new FavResponse(fav.getId());
        return response;
    }

    public void deleteFromFav(String id) throws Exception{
        Optional<Favourites>favourites=favRepo.findById(id);
        if (favourites.isEmpty()){
            throw new Exception("Fav id does not exist");
        }
        favRepo.deleteById(id);
    }

    public List<Favourites>showAllFav(){
        List fav=favRepo.findAll();
        return fav;
    }

    public List<Favourites>showAllFavByUserId(String id){
        List fav=favRepo.findAllByUser_Id(id);
        return fav;
    }

}
