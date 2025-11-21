package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Dto.CreateEventDTO;
import com.example.email_notifier_backend.Dto.EventResponseDTO;
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
    public EventResponseDTO create(Long id,CreateEventDTO dto) {

        // Convert DTO → Entity
        Events event = mapper.map(dto, Events.class);

        // Fetch user from database
        User user = userRepo.findById((id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setUser(user);
        event.setApproved(false);  // new events are not approved by default

        // Save event
        event = eventRepo.save(event);

        // Convert Entity → DTO
        return mapper.map(event, EventResponseDTO.class);
    }

    @Override
    public EventResponseDTO update(Long id, CreateEventDTO dto) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Update allowed fields
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        if (dto.getEventDate() != null) event.setDate(dto.getEventDate());
        if (dto.getEventTime()!= null) event.setTime(dto.getEventTime());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());

        event = eventRepo.save(event);

        return mapper.map(event, EventResponseDTO.class);
    }

    @Override
    public EventResponseDTO getById(Long id) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return mapper.map(event, EventResponseDTO.class);
    }

    @Override
    public List<EventResponseDTO> getUserEvents(Long userId) {
        List<Events> events = eventRepo.findByUserId(userId);

        return events.stream()
                .map(event -> mapper.map(event, EventResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDTO> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(event -> mapper.map(event, EventResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO approveEvent(Long id) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setApproved(true);    // Mark as approved

        event = eventRepo.save(event);
        return mapper.map(event, EventResponseDTO.class);
    }

    @Override
    public EventResponseDTO rejectEvent(Long id) {
        Events event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setApproved(false);   // Mark as rejected

        event = eventRepo.save(event);
        return mapper.map(event, EventResponseDTO.class);
    }
}
