//package SFG.Street_Food_Go.Entities;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
//
//import java.time.Instant;
//
//@Entity
//@Table(name = "orders", uniqueConstraints = {}, indexes = {})
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - Kalutero gia postgreSQL database.
//    private long orderId; // auto-generated.
//
//    @Column(name = "orderNO", length = 20)
//    @Size(max = 20)
//    private String orderNumber;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "order_status", length = 50)
//    @Size(max = 50)
//    private OrderStatus orderStatus;
//
//    @ManyToOne
//    @JoinColumn(name = "restaurant_id")
//    private Restaurant orderRestaurant;
//
//    @Column(name = "placed_at", updatable = false)
//    private Instant placedAt;
//
//
//
//
//    @ManyToOne()
//    @JoinColumn(name = "person_id")
//    private Person person_Of_Order;
//    // Constructors, getters, setters.
//    public Order() {}
//
//    public Order(long orderId, String orderNumber, OrderStatus orderStatus, Restaurant orderRestaurant, Instant placedAt, Person person_Of_Order) {
//        this.orderId = orderId;
//        this.orderNumber = orderNumber;
//        this.orderStatus = orderStatus;
//        this.orderRestaurant = orderRestaurant;
//        this.placedAt = placedAt;
//        this.person_Of_Order = person_Of_Order;
//    }
//
//    public long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setOrderNumber(String orderNumber) {
//        this.orderNumber = orderNumber;
//    }
//
//    public OrderStatus getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(OrderStatus orderStatus) {
//        this.orderStatus = orderStatus;
//    }
//
//    public Restaurant getOrderRestaurant() {
//        return orderRestaurant;
//    }
//
//    public void setOrderRestaurant(Restaurant orderRestaurant) {
//        this.orderRestaurant = orderRestaurant;
//    }
//
//    public Instant getPlacedAt() {
//        return placedAt;
//    }
//
//    public void setPlacedAt(Instant placedAt) {
//        this.placedAt = placedAt;
//    }
//    public Person getPerson_Of_Order() {
//        return person_Of_Order;
//    }
//
//    public void setPerson_Of_Order(Person person_Of_Order) {
//        this.person_Of_Order = person_Of_Order;
//    }
//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "orderId=" + orderId +
//                ", orderNumber='" + orderNumber + '\'' +
//                ", orderStatus=" + orderStatus +
//                ", orderRestaurant=" + orderRestaurant +
//                ", placedAt=" + placedAt +
//                '}';
//    }
//}
//
