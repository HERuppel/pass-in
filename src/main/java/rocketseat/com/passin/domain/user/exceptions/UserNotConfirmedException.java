package rocketseat.com.passin.domain.user.exceptions;

public class UserNotConfirmedException extends RuntimeException {
  public UserNotConfirmedException(String message) {
    super(message);
  }
}
