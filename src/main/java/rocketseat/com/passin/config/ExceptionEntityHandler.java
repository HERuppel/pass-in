package rocketseat.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rocketseat.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegistered;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import rocketseat.com.passin.domain.event.exceptions.EventFullException;
import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {
  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<HttpStatusCode> handleEventNotFound(EventNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(EventFullException.class)
  public ResponseEntity<ErrorResponseDTO> hadnelEventFullException(EventFullException exception) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
  }

  @ExceptionHandler(AttendeeNotFoundException.class)
  public ResponseEntity<HttpStatusCode> handleAttendeeNotFound(AttendeeNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(AttendeeAlreadyRegistered.class)
  public ResponseEntity<HttpStatusCode> handleAttendeeAlreadyExists(AttendeeAlreadyRegistered exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }

  @ExceptionHandler(CheckInAlreadyExistsException.class)
  public ResponseEntity<HttpStatusCode> handleCheckInAlreadyExists(CheckInAlreadyExistsException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }
}
