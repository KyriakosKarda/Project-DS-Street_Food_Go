package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.models.ProductResult;
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
    public ProductController(ProductService productService, RestaurantService restaurantService) {
        this.productService = productService;
        this.restaurantService = restaurantService;
    }


    //Testing Controller MUST BE REMOVED.
    @GetMapping("/menu")
    public String test(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "showMenu";
    }

    @GetMapping("/menu/{restaurant_id}")
    public String menu(Model model,@PathVariable Long restaurant_id){
        //dexomaste ena GET req me to /menu/restaurant_id kai emfanizoume
        // gia to sigkekrimeno magazi ta product tou diladi to MENU TOU
        Restaurant res = restaurantService.getRestaurantById(restaurant_id);
        model.addAttribute("products",res.getAllProducts());
        return "showMenu";
    }

    @GetMapping("/product/new")
    public String newTest(Model model){
        Product p1 = new Product();
        model.addAttribute("p1",p1);
        model.addAttribute("restaurants",restaurantService.getRestaurants());
        return "newProduct";
    }

    @PostMapping("/product/new")
    public String newTest(Model model,@ModelAttribute("p1") Product p1){
        System.out.println(p1);
        ProductResult result = productService.createProduct(p1);
        if(result.isCreated()){
            return "redirect:/";
        }
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("restaurants",restaurantService.getRestaurants());
        model.addAttribute("error",result.getErrorMessage());
        return "newProduct";
    }
}
