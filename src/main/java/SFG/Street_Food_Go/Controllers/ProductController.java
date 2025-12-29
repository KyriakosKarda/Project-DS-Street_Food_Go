package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.Product;
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
    public ProductController(ProductService productService, RestaurantService restaurantService) {
        this.productService = productService;
        this.restaurantService = restaurantService;
    }


    @GetMapping("/product/new")
    public String product(Model model){
        Product p1 = new Product();
        model.addAttribute("p1",p1);
        model.addAttribute("restaurants",restaurantService.getRestaurants());
        return "new_product";
    }


    @PostMapping("/product/new")
    public String product(Model model,@ModelAttribute("p1") Product p1){
        System.out.println(p1);
        ProductResult result = productService.createProduct(p1);
        if(result.isCreated()){
            return "redirect:/";
        }
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("restaurants",restaurantService.getRestaurants());
        model.addAttribute("error",result.getErrorMessage());
        return "new_product";
    }


    @GetMapping("/product/edit/{prod_id}/{rest_id}")
    public String editTest(Model model,@PathVariable Integer prod_id,@AuthenticationPrincipal PersonDetails personDetails,@PathVariable Long rest_id){
        Product product = productService.getProductById(prod_id);
        model.addAttribute("product",product);
        model.addAttribute("rest_id",rest_id);
        return "edit_product";
    }

    @PostMapping("/product/edit/{prod_id}/{rest_id}")
    public String handleEditedProduct(Model model,@PathVariable Integer prod_id,@ModelAttribute Product product,@PathVariable Long rest_id){
        ProductResult productResult = productService.updateProduct(product,rest_id,prod_id);
        if(productResult.isCreated()){
            return "redirect:/menu/"+rest_id;
        }
        model.addAttribute("product",product);
        model.addAttribute("rest_id",rest_id);
        model.addAttribute("error",productResult.getErrorMessage());
        return "redirect:/product/edit/"+prod_id+'/'+rest_id;
    }
}
