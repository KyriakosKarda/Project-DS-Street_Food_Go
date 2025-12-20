package SFG.Street_Food_Go.Controllers;
import SFG.Street_Food_Go.Entities.*;
import SFG.Street_Food_Go.Repository.OrderPlacementRepository;
import SFG.Street_Food_Go.Repository.OrderRequestsRepository;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Services.OrderProcessService;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import SFG.Street_Food_Go.Services.Wrappers.OrderSubmissionFormWrapper;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.SelectProductDTO_Validation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Restaurant restaurant = restaurantService.getRestaurantById(rest_id);
        System.out.println(restaurant.toString());

        List<Product> products = productService.findByRestaurant_Id(rest_id);

        //Model Wrapper So we dont have issues when we assign values to the field
        // also only supports
        OrderSelectionProductsDTO form = new OrderSelectionProductsDTO();

        //Init As the number of the products in DB
        List<SelectedProducts> selectionsList = new ArrayList<>();
        for (Product p : products) {
            selectionsList.add(new SelectedProducts(p.getId()));
        }

        //DTO for remembering User's Input
        form.setSelections(selectionsList);

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

    @GetMapping("/viewOrder/{rest_id}")
    public String viewOrder(@PathVariable Long rest_id, Model model,@ModelAttribute("OrderSelectionProductsDTO") OrderSelectionProductsDTO selected_order_details){
        //Get Only The selected itms if its selected == true.
        List<SelectedProducts> selectedItems = orderProcessService.handleSelectionOfProducts(selected_order_details);

        //Gets the prod_id of the selected items
        //To add then in business logic
        Integer[] allProductIdsChosen = new Integer[selectedItems.size()];
        for (int i = 0; i < selectedItems.size(); i++) {
            allProductIdsChosen[i] = selectedItems.get(i).getProductId();
        }
        //Get All products according to prod_id we stored in the array
        List<Product> products = orderProcessService.getProductsProvided(allProductIdsChosen);

        List<OrderToViewDTO> orderView = orderProcessService.orderView(products,selectedItems);

        OrderSubmissionFormWrapper orderSubmissionFormWrapper = new  OrderSubmissionFormWrapper();
        orderSubmissionFormWrapper.setProducts(orderView);

        model.addAttribute("orderView",orderView);
        model.addAttribute("products",orderSubmissionFormWrapper);
        model.addAttribute("rest_id",rest_id);
        model.addAttribute("total_price",orderProcessService.getTotalPriceOfTheOrder(orderView));
        return "finalizeOrder";
    }

    //here to add Post mapping from /viewOrder/{rest_id}
    @PostMapping("/viewOrder/{rest_id}")
    public String finalizeOrder(@PathVariable Long rest_id, Model model,
                                @AuthenticationPrincipal PersonDetails loggedInUser,
                                @ModelAttribute("orderForm") OrderSubmissionFormWrapper orderForm){
        System.out.println("person_id: "+loggedInUser.getPersonId());
        boolean success = orderProcessService.saveOrder(rest_id,loggedInUser,orderForm);
        return "redirect:/orders";
    }
}
