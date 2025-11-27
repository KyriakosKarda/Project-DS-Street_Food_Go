package SFG.Street_Food_Go.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "menu", uniqueConstraints = {}, indexes = {})
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - Kalutero gia postgreSQL database.
    private long prodId; // auto-generated.

    @Column(name = "product_name", length = 100)
    @Size(max = 100)
    private String prodName;

    @Column(name = "product_description")
    private String prodDesc; // Ulika proiontos kai an xreiastei mia mikri perigrafi.

    @Column(name = "product_price")
    private float prodPrice;

    // Constructors, getters, setters.
    public Menu() {
    }

    public Menu(Long prodId, String prodName, String prodDesc, float prodPrice) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodDesc = prodDesc;
        this.prodPrice = prodPrice;
    }

    public long getProdId() {
        return prodId;
    }

    public void setProdId(long id) {
        this.prodId = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public float getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(float prodPrice) {
        this.prodPrice = prodPrice;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "prodPrice=" + prodPrice +
                ", prodDesc='" + prodDesc + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodId=" + prodId +
                '}';
    }
}
