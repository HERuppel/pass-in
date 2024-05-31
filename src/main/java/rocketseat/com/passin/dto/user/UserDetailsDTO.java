package rocketseat.com.passin.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rocketseat.com.passin.domain.address.Address;

public record UserDetailsDTO(Integer id, String name, String email, String cpf, LocalDate birthdate, LocalDateTime createdAt, @JsonIgnore String pinCode, Address address, Set<String> roles) {}
