package rocketseat.com.passin.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.domain.user.User;


public interface EventRepository extends JpaRepository<Event, Integer> {
  Page<Event> findByOwner(Pageable page, User owner);

  Page<Event> findAll(Pageable page);
}
