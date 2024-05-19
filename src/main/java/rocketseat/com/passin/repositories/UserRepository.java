package rocketseat.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import rocketseat.com.passin.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  Boolean existsByEmail(String email);

  Boolean existsByCpf(String cpf);

  UserDetails findByEmail(String email);
}
