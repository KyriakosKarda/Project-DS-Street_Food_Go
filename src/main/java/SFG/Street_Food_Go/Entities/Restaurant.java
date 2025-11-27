package SFG.Street_Food_Go.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "restaurants", uniqueConstraints = {}, indexes = {})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - Kalutero gia postgreSQL database.
    private long restId; // auto-generated.

    @Column(name = "restaurant_name", length = 255)
    @Size(max = 255)
    private String restName;

    @Column(name = "restaurant_address", length = 255)
    @Size(max = 255)
    private String restAddress;

    @Column(name = "restaurant_region", length = 255)
    @Size(max = 255)
    private String restRegion;

    @Column(name = "restaurant_stars")
    private float restStars;

    @OneToOne // Ena estatorio exei ena menu.
    @JoinColumn(name = "menu_id")
    private Menu restMenu;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Person restOwner;

    public Restaurant() {}

    public Restaurant(Long restId, String restName, String restAddress,
                      String restRegion, float restStars, Menu restMenu, Person restOwner) {
        this.restId = restId;
        this.restName = restName;
        this.restAddress = restAddress;
        this.restRegion = restRegion;
        this.restStars = restStars;
        this.restMenu = restMenu;
        this.restOwner = restOwner;
    }

    public long getRestId() {
        return restId;
    }

    public void setRestId(long restId) {
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

    public float getRestStars() {
        return restStars;
    }

    public void setRestStars(float restStars) {
        this.restStars = restStars;
    }

    public Menu getRestMenu() {
        return restMenu;
    }

    public void setRestMenu(Menu restMenu) {
        this.restMenu = restMenu;
    }

    public Person getRestOwner() {
        return restOwner;
    }

    public void setRestOwner(Person restOwner) {
        this.restOwner = restOwner;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restId=" + restId +
                ", restName='" + restName + '\'' +
                ", restAddress='" + restAddress + '\'' +
                ", restRegion='" + restRegion + '\'' +
                ", restStars=" + restStars +
                ", restMenu=" + restMenu +
                ", restOwner=" + restOwner +
                '}';
    }
}