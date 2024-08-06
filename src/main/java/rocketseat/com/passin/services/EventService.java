package rocketseat.com.passin.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.domain.user.User;
import rocketseat.com.passin.domain.user.exceptions.UserNotFoundException;
import rocketseat.com.passin.dto.address.AddressRequestDTO;
import rocketseat.com.passin.dto.event.CreateEventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.repositories.AddressRepository;
import rocketseat.com.passin.repositories.EventRepository;
import rocketseat.com.passin.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class EventService {
  @Autowired
  private final EventRepository eventRepository;
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final AddressRepository addressRepository;

  public EventResponseDTO create(CreateEventRequestDTO createEventRequest, Integer ownerId) {

    User owner = this.userRepository.findById(ownerId)
      .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

    Event newEvent = convertToEvent(createEventRequest, owner);

    this.eventRepository.save(newEvent);

    EventResponseDTO eventCreated = new EventResponseDTO(newEvent, 0);

    return eventCreated;
  }

  private Event convertToEvent(CreateEventRequestDTO createEventRequest, User owner) {
    Event newEvent = new Event();

    newEvent.setTitle(createEventRequest.title());
    newEvent.setDetails(createEventRequest.details());
    newEvent.setMaximumAttendees(createEventRequest.maximumAttendees());
    newEvent.setStartDate(createEventRequest.startDate());
    newEvent.setEndDate(createEventRequest.endDate());
    newEvent.setOwner(owner);
    newEvent.setCreatedAt(LocalDateTime.now());

    createEventRequest.address().ifPresent(addressRequest -> {
      Address newAddress = convertToAddress(addressRequest);

      this.addressRepository.save(newAddress);

      newEvent.setAddress(newAddress);
    });

    return newEvent;
  }

  private Address convertToAddress(AddressRequestDTO addressRequest) {
    Address newAddress = new Address();

    newAddress.setCountry(addressRequest.country());
    newAddress.setUf(addressRequest.uf());
    newAddress.setCity(addressRequest.city());
    newAddress.setStreet(addressRequest.street());
    newAddress.setZipcode(addressRequest.zipcode());
    newAddress.setDistrict(addressRequest.district() != null ? addressRequest.district() : null);
    newAddress.setComplement(addressRequest.complement() != null ? addressRequest.complement() : null);

    return newAddress;
  }
}
