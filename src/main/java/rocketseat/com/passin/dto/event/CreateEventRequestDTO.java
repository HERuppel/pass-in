package rocketseat.com.passin.dto.event;

import java.time.LocalDateTime;
import java.util.Optional;

import rocketseat.com.passin.domain.address.Address;

public record CreateEventRequestDTO(String title, String details, Integer maximumAttendees, LocalDateTime startDate,
    LocalDateTime endDate, Integer ownerId, Optional<Address> address) {
}
