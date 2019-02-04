package eu.enhan.validation;


/**
 *
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String field, Object value) {
        super(value.toString() + " is not a correct input value for field " + field);
    }
}
