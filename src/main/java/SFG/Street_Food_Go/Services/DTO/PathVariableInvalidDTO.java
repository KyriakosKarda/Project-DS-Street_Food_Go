package SFG.Street_Food_Go.Services.DTO;

public class PathVariableInvalidDTO {
    private Long id;
    private String reason;
    public PathVariableInvalidDTO() {}
    public PathVariableInvalidDTO(Long id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}