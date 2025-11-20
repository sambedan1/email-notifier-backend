package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Dto.EventDTO;
import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Entity.User;
import com.example.email_notifier_backend.Repository.EventsRepository;
import com.example.email_notifier_backend.Repository.UserRepository;
import com.example.email_notifier_backend.Service.EventService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpli implements EventService {
    private final EventsRepository eventRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;



    @Override
    public EventDTO create(EventDTO dto) {

        // Convert DTO → Entity
        Events event = mapper.map(dto, Events.class);

        // Fetch user from database
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setUser(user);
        event.setApproved(false);  // new events are not approved by default

        // Save event
        event = eventRepo.save(event);

        // Convert Entity → DTO
        return mapper.map(event, EventDTO.class);
    }

    @Override
    public EventDTO update(Long id, EventDTO dto) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Update allowed fields
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        if (dto.getDate() != null) event.setDate(dto.getDate());
        if (dto.getTime() != null) event.setTime(dto.getTime());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());

        event = eventRepo.save(event);

        return mapper.map(event, EventDTO.class);
    }

    @Override
    public EventDTO getById(Long id) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return mapper.map(event, EventDTO.class);
    }

    @Override
    public List<EventDTO> getUserEvents(Long userId) {
        List<Events> events = eventRepo.findByUserId(userId);

        return events.stream()
                .map(event -> mapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(event -> mapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO approveEvent(Long id) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setApproved(true);    // Mark as approved

        event = eventRepo.save(event);
        return mapper.map(event, EventDTO.class);
    }

    @Override
    public EventDTO rejectEvent(Long id) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setApproved(false);   // Mark as rejected

        event = eventRepo.save(event);
        return mapper.map(event, EventDTO.class);
    }
}
