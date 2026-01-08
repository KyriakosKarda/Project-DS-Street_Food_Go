package SFG.Street_Food_Go.Controllers;
import SFG.Street_Food_Go.Entities.*;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Services.OrderProcessService;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import SFG.Street_Food_Go.Services.Wrappers.OrderSubmissionFormWrapper;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.PlaceOrderResult;
import SFG.Street_Food_Go.Services.models.SelectProductDTO_Validation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static SFG.Street_Food_Go.Services.impl.ProductServiceImp.buildOrderSelectionForm;

@Controller
public class CartController {
    private RestaurantService  restaurantService;
    private ProductService productService;
    private OrderProcessService orderProcessService;


    public CartController(RestaurantService restaurantService, ProductService productService, OrderProcessService orderProcessService) {
        this.restaurantService = restaurantService;
        this.productService = productService;
        this.orderProcessService = orderProcessService;
    }

    //add of this logic to be added in the services
    @GetMapping("/menu/{rest_id}/cart")
    public String showMenu(@PathVariable Long rest_id, Model model){
        List<Product> products = productService.findByRestaurant_Id(rest_id);

        OrderSelectionProductsDTO form = buildOrderSelectionForm(products);
        model.addAttribute("OrderDTO",form);
        model.addAttribute("products",products);
        model.addAttribute("rest_id",String.valueOf(rest_id));
        return "selection_menu";
    }

    @PostMapping("/menu/{rest_id}/cart")
    public String showCart(@ModelAttribute OrderSelectionProductsDTO  form, Model model, @PathVariable Long rest_id, RedirectAttributes redirectAttributes){

        SelectProductDTO_Validation validation = orderProcessService.validateChoices(form);

        System.out.println(validation.getReason() + ' ' +  validation.isValid());
        if(validation.isValid()){
            redirectAttributes.addFlashAttribute("OrderSelectionProductsDTO", form);
            return "redirect:/viewOrder/"+rest_id;
        }

        List<Product> products = productService.findByRestaurant_Id(rest_id);

        model.addAttribute("OrderDTO",form);
        model.addAttribute("products",products);
        model.addAttribute("rest_id",String.valueOf(rest_id));
        model.addAttribute("error",validation.getReason());
        System.out.println(validation.getReason());
        return "selection_menu";
    }
}
