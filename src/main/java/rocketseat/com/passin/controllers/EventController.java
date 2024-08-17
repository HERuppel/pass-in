package rocketseat.com.passin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.dto.event.CreateEventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.dto.event.validation.CreateEventRequestValidator;
import rocketseat.com.passin.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

}
