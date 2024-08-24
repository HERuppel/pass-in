package rocketseat.com.passin.dto.event;

import java.time.LocalDateTime;

import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.dto.user.OwnerDetailsDTO;

public record EventDetailDTO(
    int id,
    String title,
    String details,
    LocalDateTime startDate,
    LocalDateTime endDate,
    LocalDateTime createdAt,
    OwnerDetailsDTO owner,
    Address address,
    Integer maximumAttendees,
    Integer attendeesAmount) {}
