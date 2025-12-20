package SFG.Street_Food_Go.Entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


@Entity
public class OrderPlacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id_fk")
    private OrderRequest order_request;

    @ManyToOne
    @JoinColumn(name = "productId_fk")
    private Product product;

    public OrderPlacement() {}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderRequest getOrder_request() {
        return order_request;
    }

    public void setOrder_request(OrderRequest order_request) {
        this.order_request = order_request;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public String toString() {
        return "OrderPlacement{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", order_request=" + order_request +
                ", product=" + product +
                '}';
    }
}
