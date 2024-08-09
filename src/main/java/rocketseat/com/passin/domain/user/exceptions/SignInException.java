package rocketseat.com.passin.domain.user.exceptions;

public class SignInException extends RuntimeException {
  public SignInException(String message) {
    super(message);
  }
}
