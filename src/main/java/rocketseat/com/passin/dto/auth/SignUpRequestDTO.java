package rocketseat.com.passin.dto.auth;

import java.time.LocalDate;

import rocketseat.com.passin.dto.address.AddressRequestDTO;
import rocketseat.com.passin.dto.user.UserTypeDTO;

public record SignUpRequestDTO(String name, String email, String password, String cpf, LocalDate birthdate,
    UserTypeDTO userType, AddressRequestDTO address) {
  public Boolean isValid() {
    return name() != null && !name().isEmpty() &&
        email() != null && !email().isEmpty() &&
        password() != null && !password().isEmpty() &&
        cpf() != null && !cpf().isEmpty() &&
        birthdate() != null &&
        userType() != null &&
        address() != null && address().isValid();
  }
}
