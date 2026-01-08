package SFG.Street_Food_Go.Rest.Mapper;

import SFG.Street_Food_Go.Entities.Product;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Rest.DTO.ProductDTO;
import org.springframework.stereotype.Controller;

@Controller
public class ProductMapper {
        public ProductDTO productMapper(Product product) {
            if (product == null) {
                return null;
            }

            ProductDTO dto = new ProductDTO();

                dto.setId(product.getId());
                dto.setName(product.getName());
                dto.setDescription(product.getDescription());
                dto.setPrice(product.getPrice());
                dto.setAvailable(product.getAvailable());

            Restaurant restaurant = product.getRestaurant();
                if (restaurant != null) {
                dto.setRestaurantId(restaurant.getRestId());
                dto.setRestaurantName(restaurant.getRestName());
            }
            return dto;
        }
}
