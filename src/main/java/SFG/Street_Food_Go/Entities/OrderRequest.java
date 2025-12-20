package SFG.Street_Food_Go.Entities;


import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long order_id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private LocalDateTime order_Created_Time;


    @ManyToOne
    @JoinColumn(name = "rest_id_fk")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "person_id_fk")
    private Person person_order;

    @OneToMany(mappedBy = "order_request",cascade = CascadeType.ALL)
    private List<OrderPlacement> order_placement = new ArrayList<>();

    public OrderRequest() {this.order_Created_Time = LocalDateTime.now();}

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Person getPerson_order() {
        return person_order;
    }

    public void setPerson_order(Person person_order) {
        this.person_order = person_order;
    }

    public List<OrderPlacement> getOrder_placement() {
        return order_placement;
    }

    public void setOrder_placement(List<OrderPlacement> order_placement) {
        this.order_placement = order_placement;
    }

    public LocalDateTime getOrder_Created_Time() {
        return order_Created_Time;
    }

    public void setOrder_Created_Time(LocalDateTime order_Created_Time) {
        this.order_Created_Time = order_Created_Time;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "order_id=" + order_id +
                ", orderStatus=" + orderStatus +
                ", order_Created_Time=" + order_Created_Time +
                ", restaurant=" + restaurant +
                ", person_order=" + person_order +
                ", order_placement=" + order_placement +
                '}';
    }
}
