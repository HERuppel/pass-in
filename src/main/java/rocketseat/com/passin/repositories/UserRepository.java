package rocketseat.com.passin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  Boolean existsByEmail(String email);

  Boolean existsByCpf(String cpf);

  UserDetails findByEmail(String email);

  @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
  Optional<User> findUserByEmail(@Param("email") String email);

  @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
  Optional<List<Role>> findRolesByUserId(@Param("userId") Integer userId);
}
