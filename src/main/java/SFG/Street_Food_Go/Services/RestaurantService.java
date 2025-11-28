package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    List<Restaurant> getRestaurants();
    Restaurant createRestaurant(Restaurant restaurant);
}
