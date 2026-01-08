package SFG.Street_Food_Go.Rest.Mapper;


import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Rest.DTO.RestaurantDTO;
import org.springframework.stereotype.Controller;

@Controller
public class RestaurantMapper {

    public RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getRestId());
        dto.setName(restaurant.getRestName());
        dto.setAddress(restaurant.getRestAddress());
        dto.setRegion(restaurant.getRestRegion());
        dto.setStars(restaurant.getRestStars());
        dto.setOpen(restaurant.isOpen());

        if (restaurant.getOwner() != null) {
            dto.setOwnerId(restaurant.getOwner().getId());
            dto.setOwnerName(
                    restaurant.getOwner().getName() + " " +
                            restaurant.getOwner().getSurname()
            );
        }
        return dto;
    }
}
