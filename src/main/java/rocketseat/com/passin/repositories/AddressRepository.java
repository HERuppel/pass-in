package rocketseat.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketseat.com.passin.domain.address.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {}
