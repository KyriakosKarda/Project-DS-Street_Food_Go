package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Repository.PersonRepository;
import SFG.Street_Food_Go.Repository.RestaurantRepository;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.models.RestaurantResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RestaurantServiceImp implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantServiceImp(RestaurantRepository restaurantRepository) {this.restaurantRepository = restaurantRepository;}

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.getRestaurantById(id);
    }

    @Override
    public List<Restaurant> getRestaurantByPersonId(Long id) {
        System.err.println("id: " + id);
        List<Restaurant> res = restaurantRepository.getRestaurantByOwner_Id(id);
        if(res!=null){
            return res;
        }
        System.err.println("Restaurant not found!!!!!!!!!!!!!!");
        return null;
    }

    @Override
    public RestaurantResult updateRestaurantDetails(Restaurant restaurant){
        boolean valid = PersonServiceImpl.isValidAddress(restaurant.getRestAddress());
        if(!valid){
            //here to add RestaurantResult
            return new RestaurantResult(false,"Error Processing Your Restaurant Address \n Because It Has To Have Both Name Of The Address And NO");
        }
        Restaurant saved = restaurantRepository.save(restaurant);
        if(saved!=null){
            System.err.println("Restaurant saved successfully!!!!!!!!!!!!!");
            return new RestaurantResult(true,"");
        }
        return new RestaurantResult(true,"Unexpected Error");
    }
}
