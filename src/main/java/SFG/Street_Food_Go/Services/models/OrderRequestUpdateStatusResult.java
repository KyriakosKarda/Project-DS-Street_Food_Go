package SFG.Street_Food_Go.Services.models;

public class OrderRequestUpdateStatusResult {
    private String reason;
    private boolean updated;
    public OrderRequestUpdateStatusResult(boolean updated,String reason) {
        this.reason = reason;
        this.updated = updated;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {}
    public boolean isUpdated() {
        return updated;
    }
    public void setUpdated(boolean updated) {}
}
