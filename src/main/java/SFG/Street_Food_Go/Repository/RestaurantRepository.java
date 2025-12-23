package SFG.Street_Food_Go.Repository;

import SFG.Street_Food_Go.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    Restaurant getRestaurantById(Long restId);

    Restaurant getRestaurantByOwnerId(Long id);

    List<Restaurant> getRestaurantByOwner_Id(Long ownerId);
}
