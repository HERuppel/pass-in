package rocketseat.com.passin.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.UserNotFoundException;
import rocketseat.com.passin.dto.user.UserDetailsDTO;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  @Autowired
  private final UserRepository userRepository;

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
        user.get().getPinCode(),
        user.get().getAddress(),
        roleNames);

    return userDetailsDTO;
  }
}
