package rocketseat.com.passin.dto.auth.validation;

import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.user.exceptions.InvalidUserDataException;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.helpers.PatternValidator;

public class SignUpRequestValidator {
  public static void validate(SignUpRequestDTO body) {
    if (!body.isValid())
      throw new InvalidUserDataException(ErrorMessages.INVALID_SIGNUP_DATA);

    if (!PatternValidator.isEmailValid(body.user().email()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_EMAIL);

    if (!PatternValidator.isCpfValid(body.user().cpf()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_CPF);

    if (!PatternValidator.isPasswordValid(body.user().password()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_PASSWORD);

    if (!PatternValidator.isAddressZipcodeValid(body.address().zipcode()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_ZIPCODE);
  }
}
