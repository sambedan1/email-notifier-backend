package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO create(EventDTO dto);

    EventDTO update(Long id, EventDTO dto);

    EventDTO getById(Long id);

    List<EventDTO> getUserEvents(Long userId);

    List<EventDTO> getAllEvents();

    EventDTO approveEvent(Long id);

    EventDTO rejectEvent(Long id);
}
