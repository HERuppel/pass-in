package rocketseat.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketseat.com.passin.domain.role.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
