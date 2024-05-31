package rocketseat.com.passin.dto.auth;

public record ConfirmAccountRequestDTO(String email, String pinCode) {
  public Boolean isValid() {
    return email() != null && !email().isEmpty() &&
        pinCode() != null && !pinCode().isEmpty() && pinCode().length() == 6;
  }
}
