package rocketseat.com.passin.dto.auth;

import java.time.LocalDate;

import rocketseat.com.passin.dto.address.AddressRequestDTO;

public record SignUpRequestDTO(String name, String email, String password, String cpf, LocalDate birthdate, AddressRequestDTO address) {
  public Boolean isValid() {
    return name() != null && !name().isEmpty() &&
           email() != null && !email().isEmpty() &&
           password() != null && !password().isEmpty() &&
           cpf() != null && !cpf().isEmpty() &&
           birthdate() != null &&
           address() != null && address().isValid();
  }
}
