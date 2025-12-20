package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.OrderPlacement;
import SFG.Street_Food_Go.Entities.OrderRequest;
import SFG.Street_Food_Go.Entities.OrderStatus;
import SFG.Street_Food_Go.Entities.Product;
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
import SFG.Street_Food_Go.Services.models.PersonDetails;
import SFG.Street_Food_Go.Services.models.SelectProductDTO_Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public boolean saveOrder(Long rest_id, PersonDetails loggedInUser, OrderSubmissionFormWrapper orderForm) {
        Long person_id = loggedInUser.getPersonId();

        OrderRequest orderRequest = new OrderRequest();
        // we have to chamge it to PENDING but once we enter this value we get an erro from db
        // since we added this attribute to the Enum class after we created the db..
        orderRequest.setOrderStatus(OrderStatus.BEING_PREPARED);

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
        return true;
    }
}
