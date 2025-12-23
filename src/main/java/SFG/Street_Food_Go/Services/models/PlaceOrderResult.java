package SFG.Street_Food_Go.Services.models;

public class PlaceOrderResult {

    private boolean created;
    private String message;


    public PlaceOrderResult(boolean created, String message) {
        this.created = created;
        this.message = message;
    }

    public static PlaceOrderResult success(String message) {
        return new PlaceOrderResult(true, message);
    }
    public static PlaceOrderResult failure(String message) {
        return new PlaceOrderResult(false, message);
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
