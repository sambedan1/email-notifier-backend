package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Repository.EventsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {
    private final EventsRepository eventsRepository;

    public ReminderService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Transactional(readOnly = true)
    public List<Events> getEventsWithRecipients(LocalDate date) {
        return eventsRepository.findByDateWithRecipients(date);
    }
}
