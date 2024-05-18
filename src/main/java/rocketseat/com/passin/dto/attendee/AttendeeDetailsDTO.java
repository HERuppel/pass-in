package rocketseat.com.passin.dto.attendee;

import java.sql.Date;
import java.time.LocalDateTime;

public record AttendeeDetailsDTO(Integer id, String name, String email, String cpf, Date birthdate, LocalDateTime createAt, LocalDateTime checkedInAt) {}
