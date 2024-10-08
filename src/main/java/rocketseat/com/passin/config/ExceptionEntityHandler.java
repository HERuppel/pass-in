package rocketseat.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.domain.event.exceptions.InvalidEventCreationException;
import rocketseat.com.passin.domain.mail.exceptions.SendMailException;
import rocketseat.com.passin.domain.role.exceptions.NoRolesException;
import rocketseat.com.passin.domain.user.exceptions.AccessTokenNotFoundException;
import rocketseat.com.passin.domain.user.exceptions.AccountAlreadyConfirmedException;
import rocketseat.com.passin.domain.user.exceptions.InvalidPinCodeException;
import rocketseat.com.passin.domain.user.exceptions.InvalidUserDataException;
import rocketseat.com.passin.domain.user.exceptions.SignInException;
import rocketseat.com.passin.domain.user.exceptions.SignupException;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.domain.user.exceptions.UserIsNotEventOwnerException;
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
  @ExceptionHandler(InvalidPinCodeException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidPinCodeException(InvalidPinCodeException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(SignupException.class)
  public ResponseEntity<ErrorResponseDTO> handleSignupException(InvalidPinCodeException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(UserIsNotEventOwnerException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserintNotEventOwnerException(UserIsNotEventOwnerException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(NoRolesException.class)
  public ResponseEntity<ErrorResponseDTO> handleNoRolesException(NoRolesException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(InvalidEventCreationException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidEventCreationException(InvalidEventCreationException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(SignInException.class)
  public ResponseEntity<ErrorResponseDTO> handleSignInException(SignInException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(AccountAlreadyConfirmedException.class)
  public ResponseEntity<ErrorResponseDTO> handleAccountAlreadyConfirmedException(AccountAlreadyConfirmedException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleEventNotFoundException(EventNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
  }
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponseDTO> handleMissingObjectIdentificationForQueryException(MissingServletRequestParameterException exception) {
    return new ResponseEntity<>(new ErrorResponseDTO(ErrorMessages.MISSING_OBJECT_IDENTIFICATION), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<ErrorResponseDTO> handleMissingObjectIdentificationException(MissingPathVariableException exception) {
    return new ResponseEntity<>(new ErrorResponseDTO(ErrorMessages.MISSING_OBJECT_IDENTIFICATION), HttpStatus.BAD_REQUEST);
  }
}
