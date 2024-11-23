package rocketseat.com.passin.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import rocketseat.com.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
  Page<Event> findByTitleContainingIgnoreCase(Pageable page, String title);

  Page<Event> findAll(Pageable page);
}
