package rocketseat.com.passin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rocketseat.com.passin.domain.role.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
  Role findByName(String name);

  @Query(value = "SELECT * FROM roles r WHERE r.name IN (:names)", nativeQuery = true)
  List<Role> findByNames(@Param("names") List<String> names);
}
