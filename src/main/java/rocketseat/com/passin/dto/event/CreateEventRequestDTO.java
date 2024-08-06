package rocketseat.com.passin.dto.event;

import java.time.LocalDateTime;
import java.util.Optional;

import rocketseat.com.passin.dto.address.AddressRequestDTO;

public record CreateEventRequestDTO(String title, String details, Integer maximumAttendees, LocalDateTime startDate,
    LocalDateTime endDate, Optional<AddressRequestDTO> address) {
        public Boolean isValid() {
            return title() != null && !title().isEmpty() &&
                   details() != null && !details().isEmpty() &&
                   maximumAttendees() != null &&
                   startDate() != null &&
                   endDate() != null &&
                   (address().isEmpty() || address().get().isValid());
          }
}
