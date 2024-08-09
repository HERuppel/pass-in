package rocketseat.com.passin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.dto.address.AddressRequestDTO;
import rocketseat.com.passin.repositories.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
  @Autowired
  private final AddressRepository addressRepository;

  public Address create(AddressRequestDTO addressRequest) {
    Address newAddress = new Address();
    newAddress.setCountry(addressRequest.country());
    newAddress.setUf(addressRequest.uf());
    newAddress.setCity(addressRequest.city());
    newAddress.setStreet(addressRequest.street());
    newAddress.setZipcode(addressRequest.zipcode());

    if (addressRequest.complement() != null && !addressRequest.complement().isEmpty()) {
      newAddress.setComplement(addressRequest.complement());
    }

    if (addressRequest.district() != null && !addressRequest.district().isEmpty()) {
      newAddress.setDistrict(addressRequest.district());
    }

    addressRepository.save(newAddress);

    return newAddress;
  }
}
