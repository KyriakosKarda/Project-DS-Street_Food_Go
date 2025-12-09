package SFG.Street_Food_Go.Repository;

import SFG.Street_Food_Go.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


    Product getProductsByName(String name);

    Product getProductById(Integer id);

    List<Product> findByRestaurant_Id(Long restaurantId);


}
