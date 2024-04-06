package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import rocketseat.com.passin.repositories.CheckInRepository;

@Service
@RequiredArgsConstructor
public class CheckInService {
  private final CheckInRepository checkInRepository;

  public void registerCheckIn(Attendee attendee) {
    this.verifyCheckInExists (attendee.getId());

    CheckIn newCheckin = new CheckIn();
    newCheckin.setAttendee(attendee);
    newCheckin.setCreatedAt(LocalDateTime.now());

    this.checkInRepository.save(newCheckin);
  }

  public Optional<CheckIn> getCheckIn(String attendeeId) {
    return this.checkInRepository.findByAttendeeId(attendeeId);
  }

  private void verifyCheckInExists(String attendeeId) {
    Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);

    if (isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in!");

  } 
}
