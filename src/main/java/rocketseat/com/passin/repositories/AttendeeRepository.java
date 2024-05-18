package rocketseat.com.passin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketseat.com.passin.domain.attendee.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
  List<Attendee> findByEventId(Integer eventId);

  Optional<Attendee> findByEventIdAndEmail(Integer eventId, String email);
}
