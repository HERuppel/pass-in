package rocketseat.com.passin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.InvalidUserDataException;
import rocketseat.com.passin.dto.auth.SignInRequestDTO;
import rocketseat.com.passin.dto.auth.SignInResponseDTO;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.auth.SignUpResponseDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.helpers.Validator;
import rocketseat.com.passin.services.AuthService;
import rocketseat.com.passin.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  @Autowired
  private final AuthService authService;
  @Autowired
  private final TokenService tokenService;
  @Autowired
  private AuthenticationManager authenticationManager;
  
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

    if (!Validator.isAddressZipcodeValid(body.address().zipcode()))
      throw new InvalidUserDataException(ErrorMessages.INVALID_ZIPCODE);

    UserDetailsDTO createdUser = this.authService.signUp(body);

    var userPassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());

    var auth = this.authenticationManager.authenticate(userPassword);

    var authUser = (User) auth.getPrincipal();

    var token = this.tokenService.generateToken(authUser);

    return ResponseEntity.ok(new SignUpResponseDTO(createdUser, token));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO body) {
      var userPassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());

      var auth = this.authenticationManager.authenticate(userPassword);

      var authUser = (User) auth.getPrincipal();

      UserDetailsDTO user = this.authService.getUser(authUser.getId());

      var token = this.tokenService.generateToken(authUser);

      return ResponseEntity.ok(new SignInResponseDTO(user, token));
  }
  
  
}
