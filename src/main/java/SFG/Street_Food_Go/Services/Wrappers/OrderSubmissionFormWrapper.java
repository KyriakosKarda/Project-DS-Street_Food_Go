package SFG.Street_Food_Go.Services.Wrappers;

import SFG.Street_Food_Go.Services.DTO.OrderToViewDTO;

import java.util.List;

public class OrderSubmissionFormWrapper {

    private List<OrderToViewDTO> products;

    public List<OrderToViewDTO> getProducts() {
        return products;
    }

    public void setProducts(List<OrderToViewDTO> products) {
        this.products = products;
    }
}
