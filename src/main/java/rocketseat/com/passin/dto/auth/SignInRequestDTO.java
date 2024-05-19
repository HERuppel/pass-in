package rocketseat.com.passin.dto.auth;

public record SignInRequestDTO(String email, String password) {
  public Boolean isValid() {
    return email() != null && !email().isEmpty() &&
           password() != null && !password().isEmpty();
  }
}
