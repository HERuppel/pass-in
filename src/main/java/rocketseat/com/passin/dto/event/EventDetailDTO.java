package rocketseat.com.passin.dto.event;

import java.time.LocalDateTime;

import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.user.User;

public record EventDetailDTO(
    int id,
    String title,
    String details,
    LocalDateTime startDate,
    LocalDateTime endDate,
    LocalDateTime createdAt,
    User owner,
    Address address,
    Integer maximumAttendees,
    Integer attendeesAmount) {}
