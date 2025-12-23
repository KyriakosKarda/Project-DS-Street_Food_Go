package SFG.Street_Food_Go.Services.models;

import SFG.Street_Food_Go.Entities.Restaurant;

public class RestaurantResult {
    private String reason;
    private boolean created;

    public RestaurantResult successful() {
        return new RestaurantResult(true,"");
    }
    public RestaurantResult failed(String reason) {
        return new RestaurantResult(false,reason);
    }

    public RestaurantResult(){}

    public RestaurantResult(boolean created,String reason) {
        this.reason = reason;
        this.created = created;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "RestaurantResult{" +
                "reason='" + reason + '\'' +
                ", created=" + created +
                '}';
    }
}
