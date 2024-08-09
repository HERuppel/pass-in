package rocketseat.com.passin.domain.user.exceptions;

public class AccountAlreadyConfirmedException extends RuntimeException {
  public AccountAlreadyConfirmedException(String message) {
    super(message);
  }  
}
