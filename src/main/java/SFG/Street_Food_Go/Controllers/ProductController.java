package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.ProductResult;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private ProductService productService;
    private RestaurantService restaurantService;
    private PersonService personService;

    public ProductController(ProductService productService, RestaurantService restaurantService,PersonService personService) {
        this.productService = productService;
        this.restaurantService = restaurantService;
        this.personService = personService;
    }


    @GetMapping("/product/{rest_id}")
    public String product(Model model,@PathVariable Long rest_id){
        boolean result  = restaurantService.RestaurantIdExist(rest_id);
        if (!result) {
            return "redirect:/error";
        }
        Restaurant restaurant = restaurantService.getRestaurantById(rest_id);
        Product p = new Product();
        p.setRestaurant(restaurant);
        model.addAttribute("product",p);
        return "new_product";
    }


    @PostMapping("/product/new")
    public String product(Model model,@ModelAttribute Product p1,@AuthenticationPrincipal PersonDetails loggedUser){
        System.out.println(p1.getRestaurant());
        ProductResult result = productService.createProduct(p1);
        if(result.isCreated()){
            model.addAttribute("success",result.getReason());
            model.addAttribute("person",personService.getPersonById(loggedUser.getPersonId()));
            return "owner_dashboard";
        }
        model.addAttribute("error",result.getReason());
        model.addAttribute("product",p1);
        return "new_product";
    }


    @GetMapping("/product/edit/{prod_id}/{rest_id}")
    public String editTest(Model model,@PathVariable Integer prod_id,@AuthenticationPrincipal PersonDetails personDetails,@PathVariable Long rest_id){
        boolean result  = restaurantService.RestaurantIdExist(rest_id);
        if (!result) {
            return "redirect:/error";
        }
        boolean res = productService.productExistsById(prod_id);
        if (!res) {
            return "redirect:/error";
        }
        Product product = productService.getProductById(prod_id);
        model.addAttribute("product",product);
        model.addAttribute("rest_id",rest_id);
        return "edit_product";
    }

    @PostMapping("/product/edit/{prod_id}/{rest_id}")
    public String handleEditedProduct(Model model,@PathVariable Integer prod_id,@ModelAttribute Product product,@PathVariable Long rest_id){
        boolean result  = restaurantService.RestaurantIdExist(rest_id);
        if (!result) {
            return "redirect:/error";
        }
        boolean res = productService.productExistsById(prod_id);
        if (!res) {
            return "redirect:/error";
        }
        ProductResult productResult = productService.updateProduct(product,rest_id,prod_id);
        if(productResult.isCreated()){
            return "redirect:/menu/"+rest_id;
        }
        model.addAttribute("product",product);
        model.addAttribute("rest_id",rest_id);
        model.addAttribute("error",productResult.getReason());
        return "redirect:/product/edit/"+prod_id+'/'+rest_id;
    }
}
