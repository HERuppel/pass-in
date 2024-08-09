package rocketseat.com.passin.dto.auth.validation;

import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.user.exceptions.SignInException;
import rocketseat.com.passin.dto.auth.SignInRequestDTO;

public class SignInRequestValidator {
  public static void validate(SignInRequestDTO body) {
    if (!body.isValid())
      throw new SignInException(ErrorMessages.INVALID_SIGN_IN_DATA);
  }
}
