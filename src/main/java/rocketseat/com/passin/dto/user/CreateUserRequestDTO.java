package rocketseat.com.passin.dto.user;

import java.time.LocalDate;

public record CreateUserRequestDTO(String name, String email, String password, String cpf, LocalDate birthdate,
    UserTypeDTO userType) {
    public boolean isValid() {
      return name != null && !name.isEmpty() &&
        email != null && !email.isEmpty() &&
        password != null && !password.isEmpty() &&
        cpf != null && !cpf.isEmpty() &&
        birthdate != null && 
        userType != null;
    }
}
