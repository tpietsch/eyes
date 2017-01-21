package rest.v1.models;

public class ErrorJsonResponse {
    private String message;

    public ErrorJsonResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}