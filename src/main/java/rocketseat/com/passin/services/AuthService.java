package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.domain.user.exceptions.UserNotFoundException;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.repositories.AddressRepository;
import rocketseat.com.passin.repositories.RoleRepository;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final AddressRepository addressRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  

  public UserDetailsDTO signUp(SignUpRequestDTO signUpRequest) {
    String cpf = signUpRequest.cpf().replaceAll("[^0-9]", "");

    if (userRepository.existsByEmail(signUpRequest.email()))
      throw new UserAlreadyExistsException(ErrorMessages.EMAIL_ALREADY_IN_USE);
    
    if (userRepository.existsByCpf(cpf))
      throw new UserAlreadyExistsException(ErrorMessages.CPF_ALREADY_IN_USE);

    Address newAddress = new Address();
    newAddress.setCountry(signUpRequest.address().country());
    newAddress.setUf(signUpRequest.address().uf());
    newAddress.setCity(signUpRequest.address().city());
    newAddress.setStreet(signUpRequest.address().street());
    newAddress.setZipcode(signUpRequest.address().zipcode());

    if (signUpRequest.address().complement() != null && !signUpRequest.address().complement().isEmpty() ) {
      newAddress.setComplement(signUpRequest.address().complement());
    }

    if (signUpRequest.address().district() != null && !signUpRequest.address().district().isEmpty() ) {
      newAddress.setDistrict(signUpRequest.address().district());
    }
    
    addressRepository.save(newAddress);

    User newUser = new User();
    newUser.setName(signUpRequest.name());
    newUser.setEmail(signUpRequest.email());
    newUser.setPassword(passwordEncoder.encode(signUpRequest.password()));
    newUser.setCpf(cpf);
    newUser.setBirthdate(signUpRequest.birthdate());
    newUser.setCreatedAt(LocalDateTime.now());
    newUser.setAddress(newAddress);

    Role role = roleRepository.findByName("ATTENDEE");
    newUser.setRoles(Collections.singleton(role));

    String roleString = new String(role.getName());
    Set<String> rolesToReturn = new HashSet<>();
    rolesToReturn.add(roleString);

    userRepository.save(newUser);

    return new UserDetailsDTO(
      newUser.getId(), 
      newUser.getName(), 
      newUser.getEmail(), 
      newUser.getCpf(), 
      newUser.getBirthdate(), 
      newUser.getCreatedAt(),
      newAddress,
      rolesToReturn
    );
  }

  public UserDetailsDTO getUser(Integer userId) {
    Optional<User> user = this.userRepository.findById(userId);

    if (!user.isPresent()) 
      throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);

    Set<String> roleNames = user.get().getRoles().stream().map(Role::getName).collect(Collectors.toSet());

    UserDetailsDTO userDetailsDTO = new UserDetailsDTO(
      userId, 
      user.get().getName(), 
      user.get().getEmail(), 
      user.get().getCpf(), 
      user.get().getBirthdate(), 
      user.get().getCreatedAt(), 
      user.get().getAddress(), 
      roleNames
    );

    return userDetailsDTO;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email);
  }
}
