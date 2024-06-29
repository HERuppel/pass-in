package rocketseat.com.passin.domain.user.exceptions;

public class SignupException extends RuntimeException {
  public SignupException(String message) {
    super(message);
  }
}
