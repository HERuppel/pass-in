package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegistered;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import rocketseat.com.passin.dto.attendee.AttendeeDetailsDTO;
import rocketseat.com.passin.dto.attendee.AttendeesResponseDTO;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeDTO;
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

  public void verifyAttendeeSubscription(String eventId, String email) {
    Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

    if (isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyRegistered("Attendee is already registered");

  }

  public Attendee registerAttendee(Attendee newAttendee) {
    this.attendeeRepository.save(newAttendee);

    return newAttendee;
  }

  public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
    Attendee attendee = this.attendeeRepository.findById(attendeeId)
      .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));

    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

    AttendeeBadgeDTO badge = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
    return new AttendeeBadgeResponseDTO(badge);
  }
}
