package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.CreateEventDTO;
import com.example.email_notifier_backend.Dto.EventResponseDTO;
import com.example.email_notifier_backend.Service.EventService;

import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public EventResponseDTO createEvent(@RequestBody CreateEventDTO dto, Authentication auth) {
        return eventService.createEvent(dto, auth.getName());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public List<EventResponseDTO> getEvents(Authentication auth) {
        return eventService.getUserEvents(auth.getName());
    }
}