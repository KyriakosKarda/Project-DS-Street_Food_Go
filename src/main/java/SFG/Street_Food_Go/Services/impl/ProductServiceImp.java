package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Repository.ProductRepository;
import SFG.Street_Food_Go.Services.ProductService;
import SFG.Street_Food_Go.Services.models.ProductResult;
import ognl.NumericExpression;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {this.productRepository = productRepository;}

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductResult createProduct(Product product) {
        System.out.println("test");
        ProductResult res = isPriceValid(product.getPrice().toString());
        if(!res.isCreated()){
            return res;
        }
        Product p = productRepository.save(product);
        if(p == null){
            return new ProductResult(false,"Unexpected Error At creating Product");
        }
        return new ProductResult(true,null);
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public List<Product> findByRestaurant_Id(Long restaurantId) {
        return productRepository.findByRestaurant_Id(restaurantId);
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

}
