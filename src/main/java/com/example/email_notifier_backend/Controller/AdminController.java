package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.EventDTO;
import com.example.email_notifier_backend.Service.EventService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public List<EventDTO> allEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/events/{id}/approve")
    public EventDTO approveEvent(@PathVariable Long id) {
        return eventService.approveEvent(id);
    }

    @PostMapping("/events/{id}/reject")
    public EventDTO rejectEvent(@PathVariable Long id) {
        return eventService.rejectEvent(id);
    }
}
