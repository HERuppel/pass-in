package rocketseat.com.passin.domain.event.exceptions;

public class InvalidEventCreationException extends RuntimeException {
  public InvalidEventCreationException(String message) {
    super(message);
  }
}
