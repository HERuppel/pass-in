package rocketseat.com.passin.dto.auth.validation;

import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.user.exceptions.InvalidPinCodeException;
import rocketseat.com.passin.dto.auth.ConfirmAccountRequestDTO;

public class ConfirmAccountRequestValidator {
  public static void validate(ConfirmAccountRequestDTO body) {
    if (!body.isValid())
      throw new InvalidPinCodeException(ErrorMessages.INVALID_PIN_CODE);
  }
}
