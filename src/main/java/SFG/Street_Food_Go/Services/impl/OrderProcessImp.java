package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.*;
import SFG.Street_Food_Go.Repository.OrderPlacementRepository;
import SFG.Street_Food_Go.Repository.OrderRequestsRepository;
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

    public OrderProcessImp(ProductRepository productRepository, PersonService personService, OrderRequestsRepository orderRequestsRepository, OrderPlacementRepository orderPlacementRepository,ProductService productService,RestaurantService restaurantService)
    {
        this.productRepository = productRepository;
        this.personService = personService;
        this.orderRequestsRepository = orderRequestsRepository;
        this.orderPlacementRepository = orderPlacementRepository;
        this.productService = productService;
        this.restaurantService = restaurantService;
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
        // we have to chamge it to PENDING but once we enter this value we get an erro from db
        // since we added this attribute to the Enum class after we created the db..
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
            return new PlaceOrderResult(true,"Order has been saved successfully");
        }
        return new PlaceOrderResult(false,"Order has NOT been saved.Unexpected Error");
    }

    @Override
    public List<OrderRequest> getAllOrderRequests() {
        return orderRequestsRepository.findAll();
    }

    @Override
    public List<OrderPlacement> getAllOrderPlacements() {
        return orderPlacementRepository.findAll();
    }

    @Override
    public List<OrderRequest> getAllOrderRequestsByRestaurant(List<Restaurant> restaurants) {
        Long[] restaurant_ids = new Long[restaurants.size()];
        List<OrderRequest> requestList = new ArrayList<>();

        for(int i = 0; i < restaurants.size(); i++){
            restaurant_ids[i] = restaurants.get(i).getRestId();
        }

        for(int i = 0; i < restaurants.size(); i++){
            List<OrderRequest> requests = orderRequestsRepository.getOrderRequestByRestaurant_Id(restaurant_ids[i]);
            for(OrderRequest orderRequest : requests){
                requestList.add(orderRequest);
            }
        }
        int x = 0;
        for(OrderRequest list : requestList){
            x++;
        }
        System.out.println("count: " + x);
        return requestList;
    }

    @Override
    @Transactional
    public OrderRequestUpdateStatusResult markOrderPendingIfAccepted(Long orderId) {
        Optional<OrderRequest> orderRequest = orderRequestsRepository.findById(orderId);
        if(orderRequest.isPresent()){
            orderRequest.get().setOrderStatus(BEING_PREPARED);
            return new OrderRequestUpdateStatusResult(true,"Order Status Changed successfully!!!");
        }
        return new OrderRequestUpdateStatusResult(false,"Order Status Not Changed successfully!!!");
    }

    @Override
    @Transactional
    public OrderRequestUpdateStatusResult rejectOrder(Long orderId) {
        Optional<OrderRequest> orderRequest = orderRequestsRepository.findById(orderId);
        if(orderRequest.isPresent()){
            orderRequest.get().setOrderStatus(OrderStatus.DECLINED);
            return new OrderRequestUpdateStatusResult(true,"Order with Id: "+ orderId + " has been rejected");
            //Also to add message to the user for the Reason why it was Declined
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
    public OrderStatus[] getOrderStatusForActiveOrders() {
        OrderStatus[] statuses = {BEING_PREPARED, OrderStatus.ON_THE_WAY ,OrderStatus.COMPLETED};
        return statuses;
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
}
