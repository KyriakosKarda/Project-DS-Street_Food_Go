package SFG.Street_Food_Go.Services.models;

public class ProductResult {
    private boolean created;
    private String errorMessage;

    public ProductResult(boolean created, String errorMessage) {
        this.created = created;
        this.errorMessage = errorMessage;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
