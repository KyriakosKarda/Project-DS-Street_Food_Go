//package SFG.Street_Food_Go.Entities;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "order_items")
//public class OrderPlacement {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    // Σε ποια παραγγελία ανήκει
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    // Πόσα κομμάτια από αυτό το προϊόν
//    private int quantity;
//
//    public OrderPlacement() {}
//
//    public OrderPlacement(Product product, Order order, int quantity) {
//        this.product = product;
//        this.order = order;
//        this.quantity = quantity;
//    }
//
//    // getters + setters
//}
