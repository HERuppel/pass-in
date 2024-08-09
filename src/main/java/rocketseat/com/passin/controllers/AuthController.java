package rocketseat.com.passin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.dto.auth.ConfirmAccountRequestDTO;
import rocketseat.com.passin.dto.auth.ConfirmAccountResponseDTO;
import rocketseat.com.passin.dto.auth.SignInRequestDTO;
import rocketseat.com.passin.dto.auth.SignInResponseDTO;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.auth.SignUpResponseDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  @Autowired
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO body) {
    UserDetailsDTO createdUser = this.authService.createUserAndSendMail(body);

    return ResponseEntity.ok(new SignUpResponseDTO(createdUser));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO body) { 
    SignInResponseDTO signInResponse = this.authService.signIn(body.email(), body.password());

    return ResponseEntity.ok(signInResponse);
  }

  @PostMapping("/confirm-account")
  public ResponseEntity<ConfirmAccountResponseDTO> confirmAccount(@RequestBody ConfirmAccountRequestDTO body) {
    Boolean confirmPinCode = this.authService.confirmAccount(body.email(), body.pinCode());

    return ResponseEntity.ok(new ConfirmAccountResponseDTO(confirmPinCode));
  }

}
