package SFG.Street_Food_Go.Services.DTO;

public class SelectedProducts {
    boolean selected = false;
    Integer productId;
    int quantity = 0;


    public SelectedProducts(){}
    public SelectedProducts(Integer productId){
        this.productId = productId;
    }
    public SelectedProducts(boolean selected, Integer productId, int quantity) {
        this.selected = selected;
        this.productId = productId;
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SelectedProducts{" +
                "selected=" + selected +
                ", product_id=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
