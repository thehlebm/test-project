package api.pojo;

public class UnsuccessfulRegistration {
    private String error;

    public UnsuccessfulRegistration(String error) {
        this.error = error;
    }

    public UnsuccessfulRegistration() {
    }

    public String getError() {
        return error;
    }
}
