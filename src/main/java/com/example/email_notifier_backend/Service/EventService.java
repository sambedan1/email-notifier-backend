package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.CreateEventDTO;

import com.example.email_notifier_backend.Dto.EventResponseDTO;

import java.util.List;

public interface EventService {
    EventResponseDTO createEvent(CreateEventDTO dto, String userEmail);
    List<EventResponseDTO> getUserEvents(String email);
    List<EventResponseDTO> getAllEvents();
}
