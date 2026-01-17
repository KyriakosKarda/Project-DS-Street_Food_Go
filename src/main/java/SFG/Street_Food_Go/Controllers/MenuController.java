package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MenuController {
    private ProductService productService;
    private RestaurantService restaurantService;

    public MenuController(ProductService productService, RestaurantService restaurantService) {
        this.productService = productService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/menu")
    public String menu(Model model, @AuthenticationPrincipal PersonDetails personDetails){
        List<Restaurant> owners_rest = restaurantService.getRestaurantByPersonId(personDetails.getPersonId());
        model.addAttribute("res", owners_rest);
        return "owners_product_menu";
    }

    @GetMapping("/menu/{rest_id}")
    public String menu(Model model, @AuthenticationPrincipal PersonDetails personDetails, @PathVariable Long rest_id){
        boolean result  = restaurantService.RestaurantIdExist(rest_id);
        if (!result) {
            return "redirect:/error";
        }
        Restaurant res = restaurantService.getRestaurantById(rest_id);
        List<Product> products = productService.findByRestaurant_Id(rest_id);

        model.addAttribute("products", products);
        model.addAttribute("rest_id", rest_id);
        model.addAttribute("restaurant", res);
        return "product_menu_edit";
    }
}
