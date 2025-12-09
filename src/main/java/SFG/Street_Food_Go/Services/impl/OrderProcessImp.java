package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Repository.ProductRepository;
import SFG.Street_Food_Go.Services.OrderProcessService;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProcessImp implements OrderProcessService {
    private ProductRepository  productRepository;


    public OrderProcessImp(ProductRepository productRepository) {this.productRepository = productRepository;}

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
        return sum;
    }
}
