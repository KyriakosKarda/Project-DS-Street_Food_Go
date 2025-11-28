package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService{

    List<Product> getAllProducts();
    //might need to change it to a Product Result as it is at the office hopurs
    Product createProduct(Product product);
}
