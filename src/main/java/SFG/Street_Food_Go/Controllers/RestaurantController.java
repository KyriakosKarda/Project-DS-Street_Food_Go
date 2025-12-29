package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.impl.RestaurantServiceImp;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.RestaurantResult;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RestaurantController {

        private RestaurantService restaurantService;
        public RestaurantController(RestaurantServiceImp restaurantService) {this.restaurantService = restaurantService;}

        @GetMapping("/restaurants")
        public String restaurants(Model model){
            model.addAttribute("res",restaurantService.getRestaurants());
            return "show_restaurants";
        }

        @GetMapping("/restaurant")
        public String restaurant(Model model, @AuthenticationPrincipal PersonDetails loggedInOwner){
            List<Restaurant> restaurant =  restaurantService.getRestaurantByPersonId(loggedInOwner.getPersonId());
            model.addAttribute("res",restaurant);
            return  "restaurant";
        }

        @GetMapping("/restaurant/{rest_id}")
        public String restaurant(Model model, @PathVariable Long rest_id, @AuthenticationPrincipal PersonDetails loggedInOwner){
            Restaurant restaurant =  restaurantService.getRestaurantById(rest_id);
            model.addAttribute("res",restaurant);
            model.addAttribute("rest_id",rest_id);
            return  "edit_restaurant";
        }

        @PostMapping("/restaurant/{rest_id}")
        public String restaurantUpdateForm(Model model, @PathVariable Long rest_id, @AuthenticationPrincipal PersonDetails loggedInOwner, @ModelAttribute Restaurant restaurant){
            RestaurantResult result = restaurantService.updateRestaurantDetails(restaurant);
            if(!result.isCreated()){
                model.addAttribute("res",restaurant);
                model.addAttribute("rest_id",rest_id);
                model.addAttribute("error",result.getReason());
                return  "edit_restaurant";
            }
            return "redirect:/restaurant";
        }
}
