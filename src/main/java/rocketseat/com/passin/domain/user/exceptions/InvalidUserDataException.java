package rocketseat.com.passin.domain.user.exceptions;

public class InvalidUserDataException extends RuntimeException {
  public InvalidUserDataException(String message) {
    super(message);
  }
}
