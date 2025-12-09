package SFG.Street_Food_Go.Services.models;

import SFG.Street_Food_Go.Services.DTO.SelectedProducts;

import java.util.List;

public class SelectProductDTO_Validation {
    private boolean valid;
    private String reason;
    private List<SelectedProducts> selectedProducts;

    public SelectProductDTO_Validation() {}
    public SelectProductDTO_Validation(boolean valid, String reason, List<SelectedProducts> selectedProducts) {
        this.valid = valid;
        this.reason = reason;
        this.selectedProducts = selectedProducts;
    }

    public static SelectProductDTO_Validation success() {
        return new SelectProductDTO_Validation(true, null, null);
    }

    public static SelectProductDTO_Validation failure(String reason) {
        return new SelectProductDTO_Validation(true, reason, null);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<SelectedProducts> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<SelectedProducts> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    @Override
    public String toString() {
        return "SelectProductDTOReq{" +
                "valid=" + valid +
                ", reason='" + reason + '\'' +
                ", selectedProducts=" + selectedProducts +
                '}';
    }
}
