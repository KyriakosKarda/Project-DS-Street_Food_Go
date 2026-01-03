package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Entities.OrderRequest;
import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Services.OrderProcessService;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.Wrappers.OrderSubmissionFormWrapper;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.PlaceOrderResult;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ViewOrderController {
    private RestaurantService restaurantService;
    private ProductService productService;
    private OrderProcessService orderProcessService;


    public ViewOrderController(RestaurantService restaurantService, ProductService productService, OrderProcessService orderProcessService) {
        this.restaurantService = restaurantService;
        this.productService = productService;
        this.orderProcessService = orderProcessService;
    }

    @GetMapping("/viewOrder/{rest_id}")
    public String viewOrder(@PathVariable Long rest_id, Model model, @ModelAttribute("OrderSelectionProductsDTO") OrderSelectionProductsDTO selected_order_details){
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
        return "finalize_order";
    }

    @PostMapping("/viewOrder/{rest_id}")
    public String finalizeOrder(@PathVariable Long rest_id, Model model,
                                @AuthenticationPrincipal PersonDetails loggedInUser,
                                @ModelAttribute("orderForm") OrderSubmissionFormWrapper orderForm){
        System.out.println("person_id: "+loggedInUser.getPersonId());
        PlaceOrderResult result = orderProcessService.saveOrder(rest_id,loggedInUser,orderForm);
        if(result.isCreated()) {
            model.addAttribute("success",result.getMessage());
            model.addAttribute("person_id",loggedInUser.getPersonId());
            return "customer_orders";
        }
        model.addAttribute("error",result.getMessage());
        return "redirect:/viewOrder/"+rest_id;
    }

    @GetMapping("/customer_dashboard")
    public String customerDashboard(Model model,@AuthenticationPrincipal PersonDetails loggedInUser){
        model.addAttribute("person_id",loggedInUser.getPersonId());
        return "customer_orders";
    }

    @GetMapping("/viewOrder/{person_id}/active")
    public String viewOrderActive(@PathVariable Long person_id, Model model){
        List<OrderRequest> orderRequests = orderProcessService.getActiveOrderRequestByPersonId(person_id);
        System.out.println("orderRequests: "+orderRequests.size());
        model.addAttribute("orders",orderRequests);
        return "active_orders_customers";
    }

    @GetMapping("/viewOrder/{person_id}/pending")
    public String viewOrderPending(@PathVariable Long person_id, Model model){
        List<OrderRequest> orderRequests = orderProcessService.getPendingOrderRequestByPersonId(person_id);
        System.out.println("orderRequests: "+orderRequests.size());
        model.addAttribute("orders",orderRequests);
        return "pending_orders_customers";
    }

    @GetMapping("/viewOrder/{person_id}/rejected")
    public String viewOrderRejected(@PathVariable Long person_id, Model model){
        List<OrderRequest> orderRequests = orderProcessService.getRejectedOrderRequestByPersonId(person_id);
        System.out.println("orderRequests: "+orderRequests.size());
        model.addAttribute("orders",orderRequests);
        return "rejected_orders_customers";
    }
}
