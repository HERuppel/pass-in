package rocketseat.com.passin.dto.event;

public record EventDetailDTO(
    int id,
    String title,
    String details,
    String slug,
    Integer maximumAttendees,
    Integer attendeesAmount) {}
