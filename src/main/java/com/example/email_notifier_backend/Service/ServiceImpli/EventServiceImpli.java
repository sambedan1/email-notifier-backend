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
    @Autowired private EventsRepository eventRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ModelMapper modelMapper;

    @Override
    public EventResponseDTO createEvent(CreateEventDTO dto, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Events event = modelMapper.map(dto, Events.class);
        event.setUser(user);
        Events saved = eventRepo.save(event);

        EventResponseDTO response = modelMapper.map(event, EventResponseDTO.class);
        response.setUserId(event.getUser() != null ? event.getUser().getId() : null);
        return response;

    }

    @Override
    public List<EventResponseDTO> getUserEvents(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return eventRepo.findByUser(user).stream()
                .map(e -> modelMapper.map(e, EventResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDTO> getAllEvents() {
        return eventRepo.findAll().stream()
                .map(e -> modelMapper.map(e, EventResponseDTO.class))
                .collect(Collectors.toList());
    }
}
