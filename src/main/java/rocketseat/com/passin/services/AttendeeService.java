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

@Service
@RequiredArgsConstructor
public class AttendeeService {
  private final AttendeeRepository attendeeRepository;
  private final CheckInService checkInService;

  public List<Attendee> getAllAttendeesFromEvent(Integer eventId) {
    return this.attendeeRepository.findByEventId(eventId);
  }

  public AttendeesResponseDTO getEventsAttendee(Integer eventId) {
    List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);

    List<AttendeeDetailsDTO> attendeeDetails = attendees.stream().map(attendee -> {
      Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());

      LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;

      return new AttendeeDetailsDTO(
        attendee.getId(),
        attendee.getName(), 
        attendee.getEmail(),
        attendee.getCpf(), 
        attendee.getBirthdate(), 
        attendee.getCreatedAt(),
        checkedInAt
      );
    }).toList();

    return new AttendeesResponseDTO(attendeeDetails);
  }

  public void verifyAttendeeSubscription(Integer eventId, String email) {
    Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

    if (isAttendeeRegistered.isPresent())
      throw new AttendeeAlreadyRegistered("Attendee is already registered");

  }

  public Attendee registerAttendee(Attendee newAttendee) {
    this.attendeeRepository.save(newAttendee);

    return newAttendee;
  }

  public AttendeeBadgeResponseDTO getAttendeeBadge(Integer attendeeId, UriComponentsBuilder uriComponentsBuilder) {
    Attendee attendee = this.getAttendee(attendeeId);

    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri()
        .toString();

    AttendeeBadgeDTO badge = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri,
        attendee.getEvent().getId());
    return new AttendeeBadgeResponseDTO(badge);
  }

  public void checkInAttendee(Integer attendeeId) {
    Attendee attendee = this.getAttendee(attendeeId);

    this.checkInService.registerCheckIn(attendee);
  }

  private Attendee getAttendee(Integer attendeeId) {
    return this.attendeeRepository.findById(attendeeId)
        .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId.toString()));
  }
}
