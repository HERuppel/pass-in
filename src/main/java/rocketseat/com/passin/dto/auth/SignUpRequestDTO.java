package rocketseat.com.passin.dto.auth;

import java.sql.Date;

import rocketseat.com.passin.domain.address.Address;

public record SignUpRequestDTO(String name, String email, String password, String cpf, Date birthdate, Address address) {

  public Boolean isValid() {
    return name() != null && !name().isEmpty() &&
           email() != null && !email().isEmpty() &&
           password() != null && !password().isEmpty() &&
           cpf() != null && !cpf().isEmpty() &&
           birthdate() != null &&
           address() != null;
  }
}
