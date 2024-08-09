package rocketseat.com.passin.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.AccountAlreadyConfirmedException;
import rocketseat.com.passin.domain.user.exceptions.InvalidPinCodeException;
import rocketseat.com.passin.domain.user.exceptions.SignupException;
import rocketseat.com.passin.domain.user.exceptions.UserNotConfirmedException;
import rocketseat.com.passin.domain.user.exceptions.UserNotFoundException;
import rocketseat.com.passin.dto.auth.SignInResponseDTO;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final EmailService emailService;
  @Autowired
  private final UserService userService;
  @Autowired
  private final TokenService tokenService;
  @Lazy
  @Autowired
  private AuthenticationManager authenticationManager;
  Logger logger = LoggerFactory.getLogger(AuthService.class);

  @Transactional
  public UserDetailsDTO createUserAndSendMail(SignUpRequestDTO signUpRequest) {
    try {
      UserDetailsDTO createdUser = this.signUp(signUpRequest);

      emailService.sendPinToEmail(createdUser.email(), createdUser.pinCode());
  
      return createdUser;
    } catch (Exception e) {
      throw new SignupException(ErrorMessages.SIGNUP_ERROR);
    }
  }

  @Transactional
  public SignInResponseDTO signIn(String email, String password) {
    var userPassword = new UsernamePasswordAuthenticationToken(email, password);

    var auth = this.authenticationManager.authenticate(userPassword);

    var authUser = (User) auth.getPrincipal();

    UserDetailsDTO user = this.userService.get(authUser.getId());
    logger.info(user.toString());

    if (user.pinCode() != null) {
      throw new UserNotConfirmedException(ErrorMessages.USER_NOT_CONFIRMED);
    }

    var token = this.tokenService.generateToken(authUser);

    return new SignInResponseDTO(user, token);
  }

  @Transactional
  public Boolean confirmAccount(String email, String pinCode) {
    Optional<User> user = this.userRepository.findUserByEmail(email);

    if (!user.isPresent())
      throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);

    if (user.get().getPinCode() == null) 
      throw new AccountAlreadyConfirmedException(ErrorMessages.ACCOUNT_ALREADY_CONFIRMED);

    User userToUpdate = user.get();

    if (!userToUpdate.getPinCode().toString().trim().equals(pinCode.toString().trim()))
      throw new InvalidPinCodeException(ErrorMessages.INVALID_PIN_CODE);

    userToUpdate.setPinCode(null);
    userRepository.save(userToUpdate);

    return true;
  }

  @Transactional
  private UserDetailsDTO signUp(SignUpRequestDTO signUpRequest) {
    User newUser = this.userService.create(signUpRequest.user(), signUpRequest.address());

    Set<String> rolesToReturn = newUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

    return new UserDetailsDTO(
        newUser.getId(),
        newUser.getName(),
        newUser.getEmail(),
        newUser.getCpf(),
        newUser.getBirthdate(),
        newUser.getCreatedAt(),
        newUser.getPinCode(),
        newUser.getAddress(),
        rolesToReturn);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email);
  }
}
