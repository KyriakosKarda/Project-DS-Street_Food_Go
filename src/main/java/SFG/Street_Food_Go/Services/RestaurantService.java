package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.models.RestaurantResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    List<Restaurant> getRestaurants();
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);

    List<Restaurant> getRestaurantByPersonId(Long id);

    RestaurantResult updateRestaurantDetails(Restaurant restaurant);

}
