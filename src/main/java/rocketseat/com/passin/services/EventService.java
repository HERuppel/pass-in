package rocketseat.com.passin.services;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.domain.event.exceptions.EventFullException;
import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.dto.attendee.AttendeeIdDTO;
import rocketseat.com.passin.dto.attendee.AttendeeRequestDTO;
import rocketseat.com.passin.dto.event.EventIdDTO;
import rocketseat.com.passin.dto.event.EventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.repositories.EventRepository;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;
    
    public EventResponseDTO getEventDetails(String eventId) {
      Event event = this.getEventById(eventId);
      
      List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

      return new EventResponseDTO(event, attendees.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventRequest) {
      Event newEvent = new Event();
      newEvent.setTitle(eventRequest.title());
      newEvent.setDetails(eventRequest.details());
      newEvent.setMaximumAttendees(eventRequest.maximumAttendees());
      newEvent.setSlug(createSlug(eventRequest.title()));

      this.eventRepository.save(newEvent);

      return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequest) {
      this.attendeeService.verifyAttendeeSubscription(eventId, attendeeRequest.email());

      Event event = this.getEventById(eventId);
    
     List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

     if (event.getMaximumAttendees() <= attendees.size()) throw new EventFullException("Event is full!");

     Attendee newAttendee = new Attendee();

     newAttendee.setName(attendeeRequest.name());
     newAttendee.setEmail(attendeeRequest.email());
     newAttendee.setEvent(event);
     newAttendee.setCreatedAt(LocalDateTime.now());

     this.attendeeService.registerAttendee(newAttendee); 

     return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId) {
      return this.eventRepository.findById(eventId)
        .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    private String createSlug(String text) {
      String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

      return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
        .replaceAll("[^\\w\\s]", "")
        .replaceAll("\\s+", "-")
        .toLowerCase();
    }
}
