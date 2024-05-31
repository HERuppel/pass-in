package rocketseat.com.passin.domain.user.exceptions;

public class InvalidPinCodeException extends RuntimeException {
    public InvalidPinCodeException(String message) {
        super(message);
    }
}
