package rocketseat.com.passin.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rocketseat.com.passin.domain.user.exceptions.InvalidUserDataException;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.domain.user.exceptions.UserNotFoundException;
import rocketseat.com.passin.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExists(UserAlreadyExistsException exception) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
  }

  @ExceptionHandler(InvalidUserDataException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidUserData(InvalidUserDataException exception) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<HttpStatusCode> handleUserNotFound(UserNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }
}
