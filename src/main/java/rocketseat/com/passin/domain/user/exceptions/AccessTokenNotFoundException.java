package rocketseat.com.passin.domain.user.exceptions;

public class AccessTokenNotFoundException extends RuntimeException {
  public AccessTokenNotFoundException(String message) {
    super(message);
  }
}
