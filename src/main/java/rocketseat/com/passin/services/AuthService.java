package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.repositories.RoleRepository;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserDetailsDTO signUp(SignUpRequestDTO signUpRequest) {

    if (userRepository.existsByEmail(signUpRequest.email()))
      throw new UserAlreadyExistsException("E-mail já registrado!");
    
    if (userRepository.existsByCpf(signUpRequest.cpf()))
      throw new UserAlreadyExistsException("CPF já registrado!");
    
    User newUser = new User();
    newUser.setName(signUpRequest.name());
    newUser.setEmail(signUpRequest.email());
    newUser.setPassword(passwordEncoder.encode(signUpRequest.password()));
    newUser.setCpf(signUpRequest.cpf().replaceAll("[^0-9]", ""));
    newUser.setBirthdate(signUpRequest.birthdate());
    newUser.setCreatedAt(LocalDateTime.now());

    Role role = roleRepository.findByName("ATTENDEE");
    newUser.setRoles(Collections.singleton(role));

    userRepository.save(newUser);

    return new UserDetailsDTO(
      newUser.getId(), 
      newUser.getName(), 
      newUser.getEmail(), 
      newUser.getCpf(), 
      newUser.getBirthdate(), 
      newUser.getCreatedAt(),
      newUser.getRoles()
    );
  }
}
