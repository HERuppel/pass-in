package rocketseat.com.passin.dto.attendee;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendeeDetailsDTO(Integer id, String name, String email, String cpf, LocalDate birthdate, LocalDateTime createAt, LocalDateTime checkedInAt) {}
