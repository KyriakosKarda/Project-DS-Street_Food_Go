package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.DTO.OrderSelectionProductsDTO;
import SFG.Street_Food_Go.Services.models.ProductResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService{

    List<Product> getAllProducts();

    ProductResult createProduct(Product product);

    Product getProductById(Integer id);

    List<Product> findByRestaurant_Id(Long restaurantId);

    ProductResult updateProduct(Product product,Long rest_id,Integer prod_id);

}
