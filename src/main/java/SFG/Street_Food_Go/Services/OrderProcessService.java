package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderProcessService {

    List<SelectedProducts> handleSelectionOfProducts(OrderSelectionProductsDTO selected_order_details);

    List<Product> getProductsProvided(Integer[] productIds);

    List<OrderToViewDTO> orderView(List<Product> products,List<SelectedProducts> selectedItems);

    double getTotalPriceOfTheOrder(List<OrderToViewDTO> orderToViewDTOS);
}
