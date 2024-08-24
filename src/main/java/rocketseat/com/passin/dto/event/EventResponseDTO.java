package rocketseat.com.passin.dto.event;

import lombok.Getter;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.dto.user.OwnerDetailsDTO;

@Getter()
public class EventResponseDTO {
  EventDetailDTO event;

  public EventResponseDTO(Event event, Integer numberOfAttendees) {
    this.event = new EventDetailDTO(
        event.getId(),
        event.getTitle(),
        event.getDetails(),
        event.getStartDate(),
        event.getEndDate(),
        event.getCreatedAt(),
        new OwnerDetailsDTO(event.getOwner().getEmail(), event.getOwner().getName()),
        event.getAddress(),
        event.getMaximumAttendees(),
        numberOfAttendees);
  }
}
