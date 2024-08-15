package rocketseat.com.passin.dto.event.validation;

import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.event.exceptions.InvalidEventCreationException;
import rocketseat.com.passin.dto.event.CreateEventRequestDTO;

public class CreateEventRequestValidator {
  public static void validate(CreateEventRequestDTO body) {
    if (!body.isValid())
      throw new InvalidEventCreationException(ErrorMessages.INVALID_EVENT_CREATION_DATA);
  }
}
