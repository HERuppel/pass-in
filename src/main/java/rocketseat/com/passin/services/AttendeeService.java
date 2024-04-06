package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.dto.attendee.AttendeeDetailsDTO;
import rocketseat.com.passin.dto.attendee.AttendeesResponseDTO;
import rocketseat.com.passin.repositories.AttendeeRepository;
import rocketseat.com.passin.repositories.CheckInRepository;

@Service
@RequiredArgsConstructor
public class AttendeeService {
  private final AttendeeRepository attendeeRepository;
  private final CheckInRepository checkInRepository;

  public List<Attendee> getAllAttendeesFromEvent(String eventId) {
    return this.attendeeRepository.findByEventId(eventId);
  }

  public AttendeesResponseDTO getEventsAttendee(String eventId) {
    List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);

    List<AttendeeDetailsDTO> attendeeDetails = attendees.stream().map(attendee -> {
      Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());

      LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;

      return new AttendeeDetailsDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
    }).toList();

    return new AttendeesResponseDTO(attendeeDetails);
  }
}
