package SFG.Street_Food_Go.Services.models;

public class ProductResult {
    private boolean created;
    private String reason;

    public ProductResult(boolean created, String reason) {
        this.created = created;
        this.reason = reason;
    }

    public static ProductResult failure(boolean created,String errorMessage){
        return new ProductResult(created,errorMessage);
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
