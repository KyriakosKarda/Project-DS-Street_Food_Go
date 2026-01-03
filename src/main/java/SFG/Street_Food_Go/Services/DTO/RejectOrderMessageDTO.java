package SFG.Street_Food_Go.Services.DTO;

public class RejectOrderMessageDTO {

    private Long id_of_order;
    private String message;
    public RejectOrderMessageDTO() {}

    public RejectOrderMessageDTO(Long id_of_order, String message) {
        this.id_of_order = id_of_order;
        this.message = message;
    }

    public Long getId_of_order() {
        return id_of_order;
    }

    public void setId_of_order(Long id_of_order) {
        this.id_of_order = id_of_order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RejectOrderMessageDTO{" +
                "id_of_order=" + id_of_order +
                ", message='" + message + '\'' +
                '}';
    }
}
