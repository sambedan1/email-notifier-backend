package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.EventDTO;
import com.example.email_notifier_backend.Service.EventService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO dto) {
        return eventService.create(dto);
    }

    @PutMapping("/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody EventDTO dto) {
        return eventService.update(id, dto);
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<EventDTO> getUserEvents(@PathVariable Long userId) {
        return eventService.getUserEvents(userId);
    }
}
