package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.OrderPlacement;
import SFG.Street_Food_Go.Entities.OrderRequest;
import SFG.Street_Food_Go.Repository.OrderPlacementRepository;
import SFG.Street_Food_Go.Repository.OrderRequestsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestControllerForOrders {
    // TO remove pero from controller and replace it with the corresponding  Service!!!
    private OrderPlacementRepository orderPlacementRepository;
    private OrderRequestsRepository  orderRequestsRepository;
    public TestControllerForOrders(OrderPlacementRepository orderPlacementRepository,  OrderRequestsRepository orderRequestsRepository) {
        this.orderPlacementRepository = orderPlacementRepository;
        this.orderRequestsRepository = orderRequestsRepository;
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        List<OrderRequest> req = orderRequestsRepository.findAll();
        List<OrderPlacement> placements = orderPlacementRepository.findAll();

        System.out.println("size of requests order: "+req.size());
        System.out.println("size of placement order: "+placements.size());

        model.addAttribute("orderPlacements", orderPlacementRepository.findAll());
        model.addAttribute("orderRequests", orderRequestsRepository.findAll());
        return "orders";
    }
}
