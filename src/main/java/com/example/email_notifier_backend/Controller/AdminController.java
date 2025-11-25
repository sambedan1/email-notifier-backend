package com.example.email_notifier_backend.Controller;


import com.example.email_notifier_backend.Dto.EventResponseDTO;
import com.example.email_notifier_backend.Service.EventService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    private EventService eventService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-events")
    public List<EventResponseDTO> getAllEvents() {
        return eventService.getAllEvents();
    }
}
