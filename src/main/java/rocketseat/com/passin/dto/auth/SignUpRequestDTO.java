package rocketseat.com.passin.dto.auth;

import java.sql.Date;

public record SignUpRequestDTO(String name, String email, String password, String cpf, Date birthdate) {

  public Boolean isValid() {
    return name() != null && !name().isEmpty() &&
           email() != null && !email().isEmpty() &&
           password() != null && !password().isEmpty() &&
           cpf() != null && !cpf().isEmpty() &&
           birthdate() != null;
  }
}
