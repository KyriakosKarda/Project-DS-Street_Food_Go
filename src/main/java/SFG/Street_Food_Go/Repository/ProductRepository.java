package SFG.Street_Food_Go.Repository;

import SFG.Street_Food_Go.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


    Product getProductsByName(String name);

    Product getProductById(Integer id);
}
