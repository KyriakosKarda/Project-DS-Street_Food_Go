package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.*;
import SFG.Street_Food_Go.Port.SmsNotificationPort;
import SFG.Street_Food_Go.Repository.OrderPlacementRepository;
import SFG.Street_Food_Go.Repository.OrderRequestsRepository;
import SFG.Street_Food_Go.Services.DTO.RejectOrderMessageDTO;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Repository.ProductRepository;
import SFG.Street_Food_Go.Services.OrderProcessService;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.RestaurantService;
import SFG.Street_Food_Go.Services.Wrappers.OrderSubmissionFormWrapper;
import SFG.Street_Food_Go.Services.models.OrderRequestUpdateStatusResult;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.PlaceOrderResult;
import SFG.Street_Food_Go.Services.models.SelectProductDTO_Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static SFG.Street_Food_Go.Entities.OrderStatus.BEING_PREPARED;

@Service
public class OrderProcessImp implements OrderProcessService {
    private ProductRepository  productRepository;
    private PersonService personService;
    private ProductService productService;
    private RestaurantService restaurantService;
    private OrderRequestsRepository orderRequestsRepository;
    private OrderPlacementRepository orderPlacementRepository;
    private final SmsNotificationPort smsNotificationPort;

    public OrderProcessImp(ProductRepository productRepository, PersonService personService, OrderRequestsRepository orderRequestsRepository, OrderPlacementRepository orderPlacementRepository,ProductService productService,RestaurantService restaurantService,SmsNotificationPort smsNotificationPort)
    {
        this.productRepository = productRepository;
        this.personService = personService;
        this.orderRequestsRepository = orderRequestsRepository;
        this.orderPlacementRepository = orderPlacementRepository;
        this.productService = productService;
        this.restaurantService = restaurantService;
        this.smsNotificationPort = smsNotificationPort;
    }

    @Override
    public List<SelectedProducts> handleSelectionOfProducts(OrderSelectionProductsDTO selected_order_details) {
        List<SelectedProducts> selectedItems = selected_order_details.getSelections();
        List<SelectedProducts> actualSelectedProducts = new ArrayList<>();
        for (SelectedProducts selectedItem : selectedItems) {
            if(selectedItem.isSelected()){
                actualSelectedProducts.add(selectedItem);
            }
        }
        return actualSelectedProducts;
    }

    @Override
    public List<Product> getProductsProvided(Integer[] productIds) {
        List<Product> products = new ArrayList<>();
        for (Integer productId : productIds) {
            Product p = productRepository.getProductById(productId);
            products.add(p);
        }
        return products;
    }

    @Override
    public List<OrderToViewDTO> orderView(List<Product> products, List<SelectedProducts> selectedItems) {
        List<OrderToViewDTO> orderToViewDTOs = new ArrayList<>();

        for(int i = 0; i < selectedItems.size(); i++){
            Product product = products.get(i);
            SelectedProducts selectedItem = selectedItems.get(i);

            OrderToViewDTO orderToViewDTO = new OrderToViewDTO(product.getId(),product.getName(),product.getPrice());
            orderToViewDTO.setQuantity(selectedItem.getQuantity());
            orderToViewDTOs.add(orderToViewDTO);
        }
        for(OrderToViewDTO orderToViewDTO : orderToViewDTOs){
            System.out.println(orderToViewDTO);
        }
        return orderToViewDTOs;
    }

    @Override
    public double getTotalPriceOfTheOrder(List<OrderToViewDTO> orderToViewDTOS) {
        double sum = 0;
        for(OrderToViewDTO order : orderToViewDTOS){
            sum += order.getPrice() * order.getQuantity();
        }
        return Math.ceil(sum * 10) / 10.0;
    }

    @Override
    public SelectProductDTO_Validation validateChoices(OrderSelectionProductsDTO selected_order_details) {
        List<SelectedProducts> selectedItems = selected_order_details.getSelections();


        boolean emptyForm = true;
        for(SelectedProducts selectedItem : selectedItems){
            if(selectedItem.isSelected() && selectedItem.getQuantity() == 0){
                emptyForm = false;
                return new SelectProductDTO_Validation(false,"Once You Select A Product You Also MUST Choose its quality",null);
            }
            if(!selectedItem.isSelected() && selectedItem.getQuantity() > 0){
                emptyForm =  false;
                return new SelectProductDTO_Validation(false,"You Also Have To Check The Product You Want Not Only The Quantity",null);
            }
            if(selectedItem.isSelected() && selectedItem.getQuantity() > 0){
                emptyForm = false;
            }
        }
        if(emptyForm){
            return new SelectProductDTO_Validation(false,"",null);
        }
        return new SelectProductDTO_Validation(true,"",null);
    }


    @Override
    @Transactional
    public PlaceOrderResult saveOrder(Long rest_id, PersonDetails loggedInUser, OrderSubmissionFormWrapper orderForm) {
        Long person_id = loggedInUser.getPersonId();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderStatus(OrderStatus.PENDING);

        orderRequest.setPerson_order(personService.getPersonById(person_id)); //who ordered
        orderRequest.setRestaurant(restaurantService.getRestaurantById(rest_id));//in which rest
        List<OrderToViewDTO>  orderToViewDTOS = orderForm.getProducts();

        //debug purpose
        for(OrderToViewDTO orderView : orderToViewDTOS){
            System.err.println("p_id: "+ orderView.getProductId());
        }


        for(OrderToViewDTO item: orderToViewDTOS){
            //for each prod and quant create new orderplacement
            OrderPlacement orderPlacement = new OrderPlacement();
            orderPlacement.setQuantity(item.getQuantity());

            orderPlacement.setProduct(productService.getProductById(item.getProductId())); //get prod by id
            orderPlacement.setOrder_request(orderRequest); //set order's id to the placement

            orderRequest.getOrder_placement().add(orderPlacement);
        }
        OrderRequest saved = orderRequestsRepository.save(orderRequest);
        if(saved != null){
            String content = String.format("Your Order Has Been Placed Successfully!. With And Id of: (#%d)",saved.getOrder_id());
            try {
                Person person = personService.getPersonById(person_id);
                this.smsNotificationPort.sendSms(person.getPhoneNumber(), content);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return new PlaceOrderResult(true,"Order has been saved successfully");
        }
        return new PlaceOrderResult(false,"Order has NOT been saved.Unexpected Error");
    }

    @Override
    @Transactional
    public OrderRequestUpdateStatusResult markOrderPendingIfAccepted(Long orderId) {
        Optional<OrderRequest> orderRequest = orderRequestsRepository.findById(orderId);
        if(orderRequest.isPresent()){
            String content = "Your Order Has been Accepted!!";
            try {
                Long person_id = orderRequest.get().getPerson_order().getId();
                Person person = personService.getPersonById(person_id);
                this.smsNotificationPort.sendSms(person.getPhoneNumber(), content);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            orderRequest.get().setOrderStatus(BEING_PREPARED);
            return new OrderRequestUpdateStatusResult(true,"Order Status Changed successfully!!!");
        }
        return new OrderRequestUpdateStatusResult(false,"Order Status Not Changed successfully!!!");
    }

    @Override
    @Transactional
    public OrderRequestUpdateStatusResult rejectOrder(Long orderId, RejectOrderMessageDTO orderMessageDTO) {
        Optional<OrderRequest> orderRequest = orderRequestsRepository.findById(orderId);
        if(orderRequest.isPresent()){
            String content = "Your Order Has been Declined" + " the reason was: { " + orderMessageDTO.getMessage() + " }";
            try {
                Long person_id = orderRequest.get().getPerson_order().getId();
                Person person = personService.getPersonById(person_id);
                this.smsNotificationPort.sendSms(person.getPhoneNumber(), content);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            orderRequest.get().setOrderStatus(OrderStatus.DECLINED);
            orderRequest.get().setRejectReason(orderMessageDTO.getMessage());

            return new OrderRequestUpdateStatusResult(true,"Order with Id: "+ orderId + " has been rejected");
        }
        return new OrderRequestUpdateStatusResult(false,"Order Status Not Changed successfully!!!");
    }

    @Override
    public List<OrderRequest> getActiveOrderRequests(List<Restaurant> restaurants) {
        Long[] restaurant_ids = new Long[restaurants.size()];
        List<OrderRequest> requestList = new ArrayList<>();

        for(int i = 0; i < restaurants.size(); i++){
            restaurant_ids[i] = restaurants.get(i).getRestId();
        }

        for(int i = 0; i < restaurants.size(); i++){
            List<OrderRequest> requests = orderRequestsRepository.getOrderRequestByRestaurant_Id(restaurant_ids[i]);
            for(OrderRequest orderRequest : requests){
                if(orderRequest.getOrderStatus() != OrderStatus.PENDING && orderRequest.getOrderStatus() != OrderStatus.DECLINED){
                    requestList.add(orderRequest);
                }
            }
        }
        return requestList;
    }

    @Override
    public List<OrderRequest> getPendingOrderRequests(List<Restaurant> restaurants) {
        Long[] restaurant_ids = new Long[restaurants.size()];
        List<OrderRequest> requestList = new ArrayList<>();

        for(int i = 0; i < restaurants.size(); i++){
            restaurant_ids[i] = restaurants.get(i).getRestId();
        }

        for(int i = 0; i < restaurants.size(); i++){
            List<OrderRequest> requests = orderRequestsRepository.getOrderRequestByRestaurant_Id(restaurant_ids[i]);
            for(OrderRequest orderRequest : requests){
                if(orderRequest.getOrderStatus() == OrderStatus.PENDING){
                    requestList.add(orderRequest);
                }
            }
        }
        return requestList;
    }

    @Override
    public List<OrderRequest> getDeclinedOrderRequests(List<Restaurant> restaurants) {
        Long[] restaurant_ids = new Long[restaurants.size()];
        List<OrderRequest> requestList = new ArrayList<>();

        for(int i = 0; i < restaurants.size(); i++){
            restaurant_ids[i] = restaurants.get(i).getRestId();
        }

        for(int i = 0; i < restaurants.size(); i++){
            List<OrderRequest> requests = orderRequestsRepository.getOrderRequestByRestaurant_Id(restaurant_ids[i]);
            for(OrderRequest orderRequest : requests){
                if(orderRequest.getOrderStatus() == OrderStatus.DECLINED){
                    requestList.add(orderRequest);
                }
            }
        }
        return requestList;
    }

    @Override
    public OrderRequest getOrderRequestById(Long orderId) {
        return orderRequestsRepository.findById(orderId).get();
    }

    @Override
    @Transactional
    public OrderRequestUpdateStatusResult updateOrderStatus(OrderRequest orderRequest,Long orderId) {
        OrderStatus orderStatus = orderRequest.getOrderStatus();
        OrderRequest order = orderRequestsRepository.findById(orderId).get();
        if(order == null){
            return new OrderRequestUpdateStatusResult(false,"Order Status Not Changed !!!");
        }
        order.setOrderStatus(orderStatus);
        OrderRequest saved = orderRequestsRepository.save(order);
        if(saved == null){
            return new OrderRequestUpdateStatusResult(false,"Error From DB at saving!!!");
        }
        String content = "Your Order Status Changed To: '" + saved.getOrderStatus() + "' ";
        try {
            Long person_id = orderRequest.getPerson_order().getId();
            Person person = personService.getPersonById(person_id);
            this.smsNotificationPort.sendSms(person.getPhoneNumber(), content);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new OrderRequestUpdateStatusResult(true,"Order Status Changed successfully!!!");
    }

    public OrderStatus getOrderStatuses(OrderStatus orderStatus){
        List<OrderStatus> statuses = new ArrayList<>();
        if(orderStatus == OrderStatus.PENDING){
            return OrderStatus.BEING_PREPARED;
        }
        else if(orderStatus == BEING_PREPARED){
            return OrderStatus.ON_THE_WAY;
        }
        else{
            return OrderStatus.COMPLETED;
        }
    }

    @Override
    public List<OrderRequest> getActiveOrderRequestByPersonId(Long personId) {
        List<OrderRequest> orderRequests = orderRequestsRepository.findByPersonId(personId);
        List<OrderRequest> activeOrderRequests = new ArrayList<>();
        for(OrderRequest orderRequest : orderRequests){
            if(orderRequest.getOrderStatus() == OrderStatus.BEING_PREPARED || orderRequest.getOrderStatus() == OrderStatus.ON_THE_WAY){
                activeOrderRequests.add(orderRequest);
            }
        }
        return activeOrderRequests;
    }

    @Override
    public List<OrderRequest> getPendingOrderRequestByPersonId(Long personId) {
        List<OrderRequest> orderRequests = orderRequestsRepository.findByPersonId(personId);
        List<OrderRequest> activeOrderRequests = new ArrayList<>();
        for(OrderRequest orderRequest : orderRequests){
            if(orderRequest.getOrderStatus() == OrderStatus.PENDING){
                activeOrderRequests.add(orderRequest);
            }
        }
        return activeOrderRequests;
    }

    @Override
    public List<OrderRequest> getRejectedOrderRequestByPersonId(Long personId) {
        List<OrderRequest> orderRequests = orderRequestsRepository.findByPersonId(personId);
        List<OrderRequest> activeOrderRequests = new ArrayList<>();
        for(OrderRequest orderRequest : orderRequests){
            if(orderRequest.getOrderStatus() == OrderStatus.DECLINED){
                activeOrderRequests.add(orderRequest);
            }
        }
        return activeOrderRequests;
    }

    @Override
    public boolean orderExistsById(Long orderId) {
        Optional<OrderRequest> order = orderRequestsRepository.findById(orderId);
        if(order.isPresent()){
            return true;
        }
        return false;
    }
}
