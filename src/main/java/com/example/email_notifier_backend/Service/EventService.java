package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.CreateEventDTO;

import com.example.email_notifier_backend.Dto.EventResponseDTO;

import java.util.List;

public interface EventService {
    EventResponseDTO create(Long id,CreateEventDTO dto);

    EventResponseDTO update(Long id,CreateEventDTO dto);

    EventResponseDTO getById(Long id);

    List<EventResponseDTO> getUserEvents(Long userId);

    List<EventResponseDTO> getAllEvents();

    EventResponseDTO approveEvent(Long id);

    EventResponseDTO rejectEvent(Long id);
}
