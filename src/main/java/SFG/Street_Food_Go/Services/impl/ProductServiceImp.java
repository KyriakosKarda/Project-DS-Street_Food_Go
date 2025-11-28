package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Repository.ProductRepository;
import SFG.Street_Food_Go.Services.ProductService;
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
    public Product createProduct(Product product) {
        //add bussines logic here
        return productRepository.save(product);
    }



}
