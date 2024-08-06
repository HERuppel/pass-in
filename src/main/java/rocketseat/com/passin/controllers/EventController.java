package rocketseat.com.passin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.event.exceptions.InvalidEventCreationException;
import rocketseat.com.passin.domain.role.Role;
import rocketseat.com.passin.domain.user.exceptions.UserIsNotEventOwnerException;
import rocketseat.com.passin.dto.event.CreateEventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.services.EventService;
import rocketseat.com.passin.services.TokenService;
import rocketseat.com.passin.services.UserService;

import java.util.List;

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
  private final TokenService tokenService;
  @Autowired
  private final UserService userService;
  @Autowired
  private final EventService eventService;

  @PostMapping
  public ResponseEntity<EventResponseDTO> create(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateEventRequestDTO body) {
      if (!body.isValid())
        throw new InvalidEventCreationException(ErrorMessages.INVALID_EVENT_CREATION_DATA);

      String token = authorizationHeader.replace("Bearer ", "");

      Integer userId = this.tokenService.extractUserIdFromToken(token);

      List<Role> roles = this.userService.getUserRoles(userId);

      boolean isEventOwner = roles.stream().noneMatch(role -> "EVENT_OWNER".equals(role.getName()));

      if (isEventOwner) {
        throw new UserIsNotEventOwnerException(ErrorMessages.USER_IS_NOT_EVENT_OWNER);
      }

      EventResponseDTO eventResponseDTO = this.eventService.create(body, userId);
      
      return ResponseEntity.ok(eventResponseDTO);
  }
  
}
