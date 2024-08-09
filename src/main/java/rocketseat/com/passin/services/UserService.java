package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.role.exceptions.NoRolesException;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.UserAlreadyExistsException;
import rocketseat.com.passin.domain.user.exceptions.UserNotFoundException;
import rocketseat.com.passin.dto.address.AddressRequestDTO;
import rocketseat.com.passin.dto.user.CreateUserRequestDTO;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.dto.user.UserTypeDTO;
import rocketseat.com.passin.helpers.PinCode;
import rocketseat.com.passin.repositories.RoleRepository;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final AddressService addressService;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final RoleRepository roleRepository;
  Logger logger = LoggerFactory.getLogger(UserService.class);

  public User create(CreateUserRequestDTO createUserRequest, AddressRequestDTO addressRequest) {

    String cpf = createUserRequest.cpf().replaceAll("[^0-9]", "");

    if (userRepository.existsByEmail(createUserRequest.email()))
      throw new UserAlreadyExistsException(ErrorMessages.EMAIL_ALREADY_IN_USE);

    if (userRepository.existsByCpf(cpf))
      throw new UserAlreadyExistsException(ErrorMessages.CPF_ALREADY_IN_USE);

    Address newAddress = this.addressService.create(addressRequest);

    User newUser = new User();
    newUser.setName(createUserRequest.name());
    newUser.setEmail(createUserRequest.email());
    newUser.setPassword(passwordEncoder.encode(createUserRequest.password()));
    newUser.setCpf(cpf);
    newUser.setBirthdate(createUserRequest.birthdate());
    newUser.setCreatedAt(LocalDateTime.now());
    newUser.setAddress(newAddress);

    Role role = roleRepository.findByName(UserTypeDTO.ATTENDEE.name());
    Set<String> rolesToReturn = new HashSet<>();

    if (createUserRequest.userType() == UserTypeDTO.ATTENDEE) {
      newUser.setRoles(Collections.singleton(role));
      String roleString = new String(role.getName());
      rolesToReturn.add(roleString);
    } else if (createUserRequest.userType() == UserTypeDTO.EVENT_OWNER) {
      role = roleRepository.findByName(UserTypeDTO.EVENT_OWNER.name());
      newUser.setRoles(Collections.singleton(role));
      String roleString = new String(role.getName());
      rolesToReturn.add(roleString);
    } else if (createUserRequest.userType() == UserTypeDTO.BOTH) {
      List<String> roleNames = Arrays.asList(UserTypeDTO.EVENT_OWNER.name(), UserTypeDTO.ATTENDEE.name());
      List<Role> roles = roleRepository.findByNames(roleNames);
      roles.forEach(r -> rolesToReturn.add(r.getName()));
      Set<Role> roleSet = new HashSet<>(roles);
      newUser.setRoles(roleSet);
    }

    String pinCode = PinCode.generate(6);

    newUser.setPinCode(pinCode);

    userRepository.save(newUser);

    return newUser;
  }

  public UserDetailsDTO get(Integer userId) {
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
        user.get().getPinCode(),
        user.get().getAddress(),
        roleNames);

    return userDetailsDTO;
  }

  public List<Role> getUserRoles(Integer userId) {
    return this.userRepository.findRolesByUserId(userId)
      .orElseThrow(() -> new NoRolesException(ErrorMessages.NO_ROLES_ERROR));
  }
}
