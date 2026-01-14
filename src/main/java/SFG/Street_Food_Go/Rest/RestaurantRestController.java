package SFG.Street_Food_Go.Rest;

import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Rest.DTO.RestaurantDTO;
import SFG.Street_Food_Go.Rest.Mapper.RestaurantMapper;
import SFG.Street_Food_Go.Services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RestaurantRestController {

    private RestaurantService restaurantService;
    private RestaurantMapper restaurantMapper;

    public RestaurantRestController(RestaurantService restaurantService, RestaurantMapper restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping("/restaurants")
    public List<RestaurantDTO> getRestaurants() {
        return restaurantService.getRestaurants().stream().map(restaurantMapper::restaurantToRestaurantDTO).toList();
    }

    @GetMapping("/restaurants/{rest_id}")
    public RestaurantDTO getRestaurant(@PathVariable Long rest_id) {
        return restaurantMapper.restaurantToRestaurantDTO(restaurantService.getRestaurantById(rest_id));
    }
}
