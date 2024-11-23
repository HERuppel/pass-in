package rocketseat.com.passin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.dto.event.CreateEventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.dto.event.UpdateEventRequestDTO;
import rocketseat.com.passin.dto.event.validation.CreateEventRequestValidator;
import rocketseat.com.passin.services.EventService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
  @Autowired
  private final EventService eventService;

  @PostMapping
  public ResponseEntity<EventResponseDTO> create(@RequestHeader("Authorization") String authorizationHeader,
      @RequestBody CreateEventRequestDTO body) {
    CreateEventRequestValidator.validate(body);

    EventResponseDTO eventResponseDTO = this.eventService.create(body, authorizationHeader.replace("Bearer ", ""));

    return ResponseEntity.ok(eventResponseDTO);
  }

  @GetMapping("/{eventId}")
  public ResponseEntity<EventResponseDTO> get(@PathVariable Integer eventId) {
    EventResponseDTO eventResponseDTO = this.eventService.get(eventId);

    return ResponseEntity.ok(eventResponseDTO);
  }

  @GetMapping
  public ResponseEntity<List<EventResponseDTO>> list(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer limit, @RequestParam(required = false) String title) {
    List<EventResponseDTO> eventsResponseDTO = this.eventService.list(page, limit, title);

    return ResponseEntity.ok(eventsResponseDTO);
  }

  @PutMapping("/{eventId}")
  public ResponseEntity<EventResponseDTO> update(@PathVariable Integer eventId,
      @RequestBody UpdateEventRequestDTO body) {
    EventResponseDTO eventResponseDTO = this.eventService.update(eventId, body);

    return ResponseEntity.ok(eventResponseDTO);
  }

  @DeleteMapping("/{eventId}")
  public ResponseEntity<Void> delete(@PathVariable Integer eventId) {
    this.eventService.delete(eventId);

    return ResponseEntity.noContent().build();
  }
}
