package rocketseat.com.passin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.user.exceptions.InvalidUserDataException;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.auth.SignUpResponseDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.helpers.Validator;
import rocketseat.com.passin.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  
  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO body) {
    if (!body.isValid())
      throw new InvalidUserDataException(ErrorMessages.INVALID_SIGNUP_DATA);

    if (!Validator.isEmailValid(body.email()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_EMAIL);

    if (!Validator.isCpfValid(body.cpf()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_CPF);

    if (!Validator.isPasswordValid(body.password()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_PASSWORD);

    UserDetailsDTO createdUser = this.authService.signUp(body);

    return ResponseEntity.ok(new SignUpResponseDTO(createdUser));
  }
  
}
