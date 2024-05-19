package rocketseat.com.passin.dto.user;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import rocketseat.com.passin.domain.address.Address;

public record UserDetailsDTO(Integer id, String name, String email, String cpf, Date birthdate, LocalDateTime createdAt, Address address, Set<String> roles) {}
