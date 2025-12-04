package SFG.Street_Food_Go.Services.models;

public class PersonResult {
    private boolean created;
    private String errorMessage;

    public PersonResult(boolean created, String errorMessage) {
        this.created = created;
        this.errorMessage = errorMessage;
    }

    public static PersonResult failure(boolean created,String errorMessage){
        return new PersonResult(created,errorMessage);
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
