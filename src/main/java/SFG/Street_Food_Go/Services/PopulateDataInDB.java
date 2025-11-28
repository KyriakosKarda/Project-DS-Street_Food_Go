package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Repository.RestaurantRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class PopulateDataInDB {

    private RestaurantRepository restaurantRepository;
    public PopulateDataInDB(RestaurantRepository restaurantRepository) {this.restaurantRepository = restaurantRepository;}

    @PostConstruct
    public void populateRestaurants(){
//        Restaurant restaurant1 = new Restaurant(null,"pita tou pappou","tsakalof 10","Koridallos",4.5);
//        Restaurant restaurant2 = new Restaurant(null,"mailos","tsakalof 20","Koridallos",5.0);
//        Restaurant restaurant3 = new Restaurant(null,"dealers","tsakalof 30","Koridallos",3.5);
//        restaurantRepository.save(restaurant1);
//        restaurantRepository.save(restaurant2);
//        restaurantRepository.save(restaurant3);
    }
}
