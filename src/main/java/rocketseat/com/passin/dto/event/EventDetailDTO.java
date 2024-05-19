package rocketseat.com.passin.dto.event;

import rocketseat.com.passin.domain.user.User;

public record EventDetailDTO(
    int id,
    String title,
    String details,
    String slug,
    User owner,
    Integer maximumAttendees,
    Integer attendeesAmount) {}
