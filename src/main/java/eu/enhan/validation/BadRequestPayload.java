package eu.enhan.validation;

/**
 *
 */
public class BadRequestPayload {

    private String message;

    public BadRequestPayload(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
