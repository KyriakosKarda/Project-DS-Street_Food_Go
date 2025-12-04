package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.impl.RestaurantServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

        private RestaurantService restaurantService;
        public RestaurantController(RestaurantServiceImp restaurantService) {this.restaurantService = restaurantService;}

        @GetMapping()
        public String restaurants(Model model){
            model.addAttribute("res",restaurantService.getRestaurants());
            return  "showRestaurants";
        }
}
