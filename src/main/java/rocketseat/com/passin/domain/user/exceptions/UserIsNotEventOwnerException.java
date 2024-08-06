package rocketseat.com.passin.domain.user.exceptions;

public class UserIsNotEventOwnerException extends RuntimeException {
  public UserIsNotEventOwnerException(String message) {
    super(message);
  }
}
