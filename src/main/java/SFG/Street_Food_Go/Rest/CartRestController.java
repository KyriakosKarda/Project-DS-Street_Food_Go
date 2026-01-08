package SFG.Street_Food_Go.Rest;


import SFG.Street_Food_Go.Rest.DTO.ProductDTO;
import SFG.Street_Food_Go.Rest.Mapper.ProductMapper;
import SFG.Street_Food_Go.Services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CartRestController {

    private ProductService productService;
    private ProductMapper  productMapper;

    public CartRestController(ProductService productService,ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/menu/{rest_id}/cart")
    public List<ProductDTO> getMenuItems(@PathVariable Long rest_id) {
        return productService.findByRestaurant_Id(rest_id).stream().map(productMapper::productMapper).toList();
    }
}
