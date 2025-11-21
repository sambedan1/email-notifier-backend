package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.CreateEventDTO;
import com.example.email_notifier_backend.Dto.EventResponseDTO;
import com.example.email_notifier_backend.Service.EventService;

import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public EventResponseDTO createEvent( @RequestParam Long userId,@RequestBody CreateEventDTO dto) {
        return eventService.create(userId,dto);
    }

    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(@PathVariable Long id, @RequestBody CreateEventDTO dto) {
        return eventService.update(id, dto);
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEvent(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<EventResponseDTO> getUserEvents(@PathVariable Long userId) {
        return eventService.getUserEvents(userId);
    }
}
