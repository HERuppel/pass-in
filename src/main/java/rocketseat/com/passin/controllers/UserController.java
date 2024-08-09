package rocketseat.com.passin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.user.exceptions.AccessTokenNotFoundException;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.services.TokenService;
import rocketseat.com.passin.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  @Autowired
  private final UserService userService;
  @Autowired
  private final TokenService tokenService;

  @GetMapping
  public ResponseEntity<UserDetailsDTO> getUser(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader == null) {
      throw new AccessTokenNotFoundException(ErrorMessages.ACCESS_TOKEN_NOT_FOUND);
    }

    String token = authorizationHeader.replace("Bearer ", "");

    Integer userId = this.tokenService.extractUserIdFromToken(token);

    UserDetailsDTO userDetailsDTO = this.userService.get(userId);

    return ResponseEntity.ok(userDetailsDTO);
  }
}
