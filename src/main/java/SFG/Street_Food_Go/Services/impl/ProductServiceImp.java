package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Repository.ProductRepository;
import SFG.Street_Food_Go.Repository.RestaurantRepository;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.DTO.SelectedProducts;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.models.ProductResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    private ProductRepository productRepository;
    private RestaurantRepository restaurantRepository;

    public ProductServiceImp(ProductRepository productRepository,RestaurantRepository restaurantRepository) {this.productRepository = productRepository;this.restaurantRepository = restaurantRepository;}

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductResult createProduct(Product product) {
        ProductResult res = isPriceValid(product.getPrice().toString());
        if(!res.isCreated()){
            return res;
        }
        Product p = productRepository.save(product);
        if(p == null){
            return new ProductResult(false,"Unexpected Error At creating Product");
        }
        return new ProductResult(true,"Successfully Created Product");
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public List<Product> findByRestaurant_Id(Long restaurantId) {
        return productRepository.findByRestaurant_Id(restaurantId);
    }

    @Override
    public ProductResult updateProduct(Product formProduct,Long rest_id,Integer prod_id) {
        ProductResult productResult = isPriceValid(formProduct.getPrice().toString());
        if(productResult.isCreated()){
            Restaurant restaurant = restaurantRepository.getRestaurantById(rest_id);
            System.out.println(restaurant.getRestId());
            Product p = productRepository.getProductById(prod_id);

            p.setAvailable(formProduct.getAvailable());
            p.setDescription(formProduct.getDescription());
            p.setPrice(formProduct.getPrice());
            p.setName(formProduct.getName());

            System.err.println(formProduct.getRestaurant().getRestId());
            productRepository.save(p);

            return new ProductResult(true,null);
        }
        return new ProductResult(false,productResult.getReason());
    }

    @Override
    public boolean productExistsById(Integer id) {
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            return true;
        }
        return false;
    }


    private ProductResult isPriceValid(String price) {
        try {
            double p = Double.parseDouble(price);
            if(p <= 0.0){
                return new ProductResult(false,"Price MUST BE a positive Number");
            }
        }catch (NumberFormatException ex){
            System.out.println("Price contains letters");
            return new ProductResult(false,"Price MUST ONLY be a Number.");
        }
        return new ProductResult(true,null);
    }


    public static OrderSelectionProductsDTO buildOrderSelectionForm(List<Product> products){
        OrderSelectionProductsDTO form = new OrderSelectionProductsDTO();

        List<SelectedProducts> selectionsList = new ArrayList<>();
        for (Product p : products) {
            selectionsList.add(new SelectedProducts(p.getId()));
        }

        form.setSelections(selectionsList);
        return form;
    }

}
