package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/menu")
    public String test(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "showMenu";
    }

    @GetMapping("/new")
    public String newTest(Model model){
        Product p1 = new Product();
        model.addAttribute("p1",p1);
        return "newProduct";
    }

    @PostMapping("/new")
    public String newTest(Model model,@ModelAttribute("p1") Product p1){
        System.out.println(p1);
        Product p = productService.createProduct(p1);
        model.addAttribute("products",productService.getAllProducts());
        return "showMenu";
    }
}
