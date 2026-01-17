package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.*;
import SFG.Street_Food_Go.Services.DTO.RejectOrderMessageDTO;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import SFG.Street_Food_Go.Services.Wrappers.OrderSubmissionFormWrapper;
import SFG.Street_Food_Go.Services.models.OrderRequestUpdateStatusResult;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.PlaceOrderResult;
import SFG.Street_Food_Go.Services.models.SelectProductDTO_Validation;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderProcessService {

    List<SelectedProducts> handleSelectionOfProducts(OrderSelectionProductsDTO selected_order_details);

    List<Product> getProductsProvided(Integer[] productIds);

    List<OrderToViewDTO> orderView(List<Product> products,List<SelectedProducts> selectedItems);

    double getTotalPriceOfTheOrder(List<OrderToViewDTO> orderToViewDTOS);

    SelectProductDTO_Validation validateChoices(OrderSelectionProductsDTO selected_order_details);

    PlaceOrderResult saveOrder(Long rest_id, PersonDetails loggedInUser, OrderSubmissionFormWrapper orderForm);

    OrderRequestUpdateStatusResult markOrderPendingIfAccepted(Long orderId);

    OrderRequestUpdateStatusResult rejectOrder(Long orderId, RejectOrderMessageDTO rejectOrderMessageDTO);

    List<OrderRequest> getActiveOrderRequests(List<Restaurant> restaurants);

    List<OrderRequest> getPendingOrderRequests(List<Restaurant> restaurants);

    List<OrderRequest> getDeclinedOrderRequests(List<Restaurant> restaurants);

    OrderRequest getOrderRequestById(Long orderId);

    OrderRequestUpdateStatusResult updateOrderStatus(OrderRequest orderRequest,Long orderId);

    OrderStatus getOrderStatuses(OrderStatus orderStatus);

    List<OrderRequest> getActiveOrderRequestByPersonId(Long personId);

    List<OrderRequest> getPendingOrderRequestByPersonId(Long personId);

    List<OrderRequest> getRejectedOrderRequestByPersonId(Long personId);

    boolean orderExistsById(Long orderId);
}
