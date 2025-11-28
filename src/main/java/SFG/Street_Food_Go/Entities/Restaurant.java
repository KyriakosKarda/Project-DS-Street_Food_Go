package SFG.Street_Food_Go.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - Kalutero gia postgreSQL database.
    @Column
    private Long restId; // auto-generated.

    @Column
    private String restName;

    @Column()
    private String restAddress;

    @Column()
    private String restRegion;

    @Column()
    private double restStars;


    @OneToMany(mappedBy = "restaurant",cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Product> allProducts;



    public Restaurant() {}

    public Restaurant(Long restId, String restName, String restAddress, String restRegion, double restStars) {
        this.restId = restId;
        this.restName = restName;
        this.restAddress = restAddress;
        this.restRegion = restRegion;
        this.restStars = restStars;
    }



    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public String getRestRegion() {
        return restRegion;
    }

    public void setRestRegion(String restRegion) {
        this.restRegion = restRegion;
    }

    public double getRestStars() {
        return restStars;
    }

    public void setRestStars(double restStars) {
        this.restStars = restStars;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restId=" + restId +
                ", restName='" + restName + '\'' +
                ", restAddress='" + restAddress + '\'' +
                ", restRegion='" + restRegion + '\'' +
                ", restStars=" + restStars +
                '}';
    }
}