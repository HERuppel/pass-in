package rocketseat.com.passin.dto.auth;

import rocketseat.com.passin.dto.address.AddressRequestDTO;
import rocketseat.com.passin.dto.user.CreateUserRequestDTO;

public record SignUpRequestDTO(CreateUserRequestDTO user, AddressRequestDTO address) {
  public Boolean isValid() {
    return user() != null && user().isValid() &&
        address() != null && address().isValid();
  }
}
