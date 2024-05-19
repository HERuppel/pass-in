package rocketseat.com.passin.dto.address;

public record AddressRequestDTO(String country, String uf, String city, String street, String zipcode, String district, String complement) {
  public Boolean isValid() {
    return country() != null && !country().isEmpty() &&
           uf() != null && !uf().isEmpty() &&
           city() != null && !city().isEmpty() &&
           street() != null && !street().isEmpty() &&
           zipcode() != null && !zipcode().isEmpty();
  }
}
