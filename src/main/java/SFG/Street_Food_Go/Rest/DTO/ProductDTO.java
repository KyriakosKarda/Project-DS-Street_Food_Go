package SFG.Street_Food_Go.Rest.DTO;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Long restaurantId;
    private String restaurantName;
    private boolean available;

    public ProductDTO(){}
    public ProductDTO(Integer id, String name, String description, Double price, Long restaurantId, String restaurantName,boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {this.available = available;}
}
