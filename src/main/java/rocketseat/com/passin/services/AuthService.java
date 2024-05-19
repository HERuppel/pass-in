package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.repositories.AddressRepository;
import rocketseat.com.passin.repositories.RoleRepository;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final AddressRepository addressRepository;
  private final PasswordEncoder passwordEncoder;

  public UserDetailsDTO signUp(SignUpRequestDTO signUpRequest) {

    if (userRepository.existsByEmail(signUpRequest.email()))
      throw new UserAlreadyExistsException("E-mail já registrado!");
    
    if (userRepository.existsByCpf(signUpRequest.cpf()))
      throw new UserAlreadyExistsException("CPF já registrado!");

    Address newAddress = new Address();
    newAddress.setCountry(signUpRequest.address().getCountry());
    newAddress.setUf(signUpRequest.address().getUf());
    newAddress.setCity(signUpRequest.address().getCity());
    newAddress.setStreet(signUpRequest.address().getStreet());
    newAddress.setZipcode(signUpRequest.address().getZipcode());

    if (signUpRequest.address().getComplement() != null && !signUpRequest.address().getComplement().isEmpty() ) {
      newAddress.setComplement(signUpRequest.address().getComplement());
    }

    if (signUpRequest.address().getDistrict() != null && !signUpRequest.address().getDistrict().isEmpty() ) {
      newAddress.setDistrict(signUpRequest.address().getDistrict());
    }
    
    addressRepository.save(newAddress);

    User newUser = new User();
    newUser.setName(signUpRequest.name());
    newUser.setEmail(signUpRequest.email());
    newUser.setPassword(passwordEncoder.encode(signUpRequest.password()));
    newUser.setCpf(signUpRequest.cpf().replaceAll("[^0-9]", ""));
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
}
