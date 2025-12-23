package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.OrderPlacement;
import SFG.Street_Food_Go.Entities.OrderRequest;
import SFG.Street_Food_Go.Repository.OrderPlacementRepository;
import SFG.Street_Food_Go.Repository.OrderRequestsRepository;
import SFG.Street_Food_Go.Services.OrderProcessService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private OrderProcessService orderProcessService;

    public OrderController(OrderProcessService orderProcessService) {
        this.orderProcessService = orderProcessService;
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        List<OrderRequest> req = orderProcessService.getAllOrderRequests();
        List<OrderPlacement> placements = orderProcessService.getAllOrderPlacements();

        System.out.println("size of requests order: "+req.size());
        System.out.println("size of placement order: "+placements.size());

        model.addAttribute("orderPlacements", placements);
        model.addAttribute("orderRequests", req);
        return "orders";
    }
}
