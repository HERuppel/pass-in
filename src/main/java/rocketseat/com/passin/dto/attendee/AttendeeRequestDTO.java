package rocketseat.com.passin.dto.attendee;

import java.sql.Date;

public record AttendeeRequestDTO(String name, String email, String cpf, Date birthdate) {}
