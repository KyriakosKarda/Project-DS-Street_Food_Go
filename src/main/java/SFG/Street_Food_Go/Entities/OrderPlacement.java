package SFG.Street_Food_Go.Entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "order_items")
@Entity
public class OrderPlacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private int quantity;

    @Column
    private LocalDate order_Created_Time;

    @ManyToOne()
    @JoinColumn(name = "order_Id_fk")
    private OrderRequest order_request;

    @ManyToOne
    @JoinColumn(name = "productId_fk")
    private Product product;

    public OrderPlacement() {this.order_Created_Time = LocalDate.now();}

    public OrderPlacement(int quantity, Long id) {
        this.quantity = quantity;
        this.id = id;
        this.order_Created_Time = LocalDate.now();;
    }

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

    public LocalDate getOrder_Created_Time() {
        return order_Created_Time;
    }

    public void setOrder_Created_Time(LocalDate order_Created_Time) {
        this.order_Created_Time = order_Created_Time;
    }
    @Override
    public String toString() {
        return "OrderPlacement{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", order_Created_Time=" + order_Created_Time +
                ", order_request=" + order_request +
                ", product=" + product +
                '}';
    }
}
