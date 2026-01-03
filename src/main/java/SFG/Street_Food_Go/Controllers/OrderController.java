package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.OrderPlacement;
import SFG.Street_Food_Go.Entities.OrderRequest;
import SFG.Street_Food_Go.Entities.OrderStatus;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Repository.OrderPlacementRepository;
import SFG.Street_Food_Go.Repository.OrderRequestsRepository;
import SFG.Street_Food_Go.Services.DTO.RejectOrderMessageDTO;
import SFG.Street_Food_Go.Services.OrderProcessService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.models.OrderRequestUpdateStatusResult;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private OrderProcessService orderProcessService;
    private RestaurantService  restaurantService;


    public OrderController(OrderProcessService orderProcessService, RestaurantService restaurantService) {
        this.orderProcessService = orderProcessService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/order/restaurant")
    public String restaurant(Model model, @AuthenticationPrincipal PersonDetails user) {
        return  "order_dashboard";
    }

    @GetMapping("/order/restaurant/active")
    public String activeOrders(Model model, @AuthenticationPrincipal PersonDetails user) {
        List<Restaurant> restaurants = restaurantService.getRestaurantByPersonId(user.getPersonId());
        List<OrderRequest> active_orders = orderProcessService.getActiveOrderRequests(restaurants);
        model.addAttribute("orders", active_orders);
        return  "active_orders";
    }

    @GetMapping("/order/restaurant/pending")
    public String pendingOrders(Model model, @AuthenticationPrincipal PersonDetails user) {
        List<Restaurant> restaurants = restaurantService.getRestaurantByPersonId(user.getPersonId());
        List<OrderRequest> pendingOrders = orderProcessService.getPendingOrderRequests(restaurants);
        model.addAttribute("orders", pendingOrders);
        return  "pending_orders";
    }

    @GetMapping("/order/restaurant/rejected")
    public String rejectedOrder(Model model, @AuthenticationPrincipal PersonDetails user) {
        List<Restaurant> restaurants = restaurantService.getRestaurantByPersonId(user.getPersonId());
        List<OrderRequest> rejected_orders = orderProcessService.getDeclinedOrderRequests(restaurants);
        model.addAttribute("orders", rejected_orders);
        return  "rejected_orders";
    }

    @GetMapping("/order/restaurant/{order_id}/accept")
    public String acceptOrder(Model model, @PathVariable Long order_id) {
        OrderRequestUpdateStatusResult orderRequestUpdateStatusResult = orderProcessService.markOrderPendingIfAccepted(order_id);
        if(orderRequestUpdateStatusResult.isUpdated()) {
            model.addAttribute("success", orderRequestUpdateStatusResult.getReason());
        }
        else{
            model.addAttribute("failure", orderRequestUpdateStatusResult.getReason());
        }
        return  "order_dashboard";
    }

    @GetMapping("/order/restaurant/{order_id}/reject/message")
    public String rejectOrderMessage(Model model, @PathVariable Long order_id){
        OrderRequest order = orderProcessService.getOrderRequestById(order_id);
        RejectOrderMessageDTO dto = new RejectOrderMessageDTO();
        dto.setId_of_order(order_id);
        model.addAttribute("order", order);
        model.addAttribute("order_id",order_id);
        model.addAttribute("rejectOrderMessageDTO",dto);
        return "order_reject_message";
    }

    @PostMapping("/order/restaurant/{order_id}/reject/message")
    public String rejectOrderMessage(Model model,@PathVariable Long order_id,@ModelAttribute RejectOrderMessageDTO rejectOrderMessageDTO){
        OrderRequestUpdateStatusResult orderRequestUpdateStatusResult = orderProcessService.rejectOrder(order_id,rejectOrderMessageDTO);
        System.out.println(rejectOrderMessageDTO.getMessage() + ' '+ rejectOrderMessageDTO.getId_of_order());
        if(orderRequestUpdateStatusResult.isUpdated()) {
            model.addAttribute("success", orderRequestUpdateStatusResult.getReason());
        }
        else{
            model.addAttribute("failure", orderRequestUpdateStatusResult.getReason());
        }
        return  "order_dashboard";
    }

    @GetMapping("/order/restaurant/{order_id}/update")
    public String updateActiveOrder(Model model, @PathVariable Long order_id) {
        OrderRequest order = orderProcessService.getOrderRequestById(order_id);
        OrderStatus status = orderProcessService.getOrderStatuses(order.getOrderStatus());
        model.addAttribute("order", order);
        model.addAttribute("status", status);
        model.addAttribute("order_id", order_id);
        return "update_order";
    }

    @PostMapping("/order/restaurant/{order_id}/update")
    public String handleUpdateActiveOrder(Model model, @AuthenticationPrincipal PersonDetails user, @PathVariable Long order_id, @ModelAttribute OrderRequest updatedOrder) {
        OrderRequestUpdateStatusResult result = orderProcessService.updateOrderStatus(updatedOrder, order_id);
        if(!result.isUpdated()){
            model.addAttribute("failure", result.getReason());
        }else {
            model.addAttribute("success", result.getReason());
        }
        return "order_dashboard";
    }


}
