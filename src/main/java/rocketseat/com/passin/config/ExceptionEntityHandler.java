package rocketseat.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rocketseat.com.passin.domain.mail.exceptions.SendMailException;
import rocketseat.com.passin.domain.user.exceptions.AccessTokenNotFoundException;
import rocketseat.com.passin.domain.user.exceptions.InvalidUserDataException;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.domain.user.exceptions.UserNotConfirmedException;
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

  @ExceptionHandler(AccessTokenNotFoundException.class)
  public ResponseEntity<HttpStatusCode> handleAccessTokenNotFound(AccessTokenNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(exception.getMessage()));
  }

  @ExceptionHandler(SendMailException.class)
  public ResponseEntity<ErrorResponseDTO> handleSendMailException(SendMailException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
  }

  @ExceptionHandler(UserNotConfirmedException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserNotConfirmedException(UserNotConfirmedException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(exception.getMessage()));
  }
}
