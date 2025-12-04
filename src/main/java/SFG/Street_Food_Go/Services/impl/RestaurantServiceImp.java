package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Repository.RestaurantRepository;
import SFG.Street_Food_Go.Services.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
/*
Εδω κανουμε λειτουργιες για τα Restaurants Saves ελενχος κλπ. απο εδω μιλαμε με το repo Απευθειας.
 */
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
    public Restaurant createRestaurant(Restaurant restaurant) {
        /// TODO REMOVE NULL
        return null;
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.getRestaurantByRestId(id);
    }
}
