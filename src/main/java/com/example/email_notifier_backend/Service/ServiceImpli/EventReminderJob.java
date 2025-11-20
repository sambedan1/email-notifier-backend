package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventReminderJob extends QuartzJobBean {

    private final EventsRepository eventRepository;
    private final EmailService emailService;

    @Override
    public void executeInternal(JobExecutionContext context){
        // LocalDate reminderDate = events.getDate().minusDays(5);
        LocalDate today = LocalDate.now();

        List<Events> events = eventRepository.findAllByEmailSentTrue();

        for (Events event : events) {
            LocalDate reminderDate = event.getDate().minusDays(5);

            if (today.equals(reminderDate)) {
                String subject = "Event Reminder: " + event.getTitle();
                String body = "Hello " + event.getUser().getName() + ",\n\n" +
                        "This is a reminder for your event \"" + event.getTitle() + "\" scheduled on " + event.getDate() + "." +
                        (event.getDescription() != null ? "\n\nDescription: " + event.getDescription() : "");

                emailService.sendEmail(event.getUser().getEmail(), subject, body);
                System.out.println("📩 Reminder sent for event: " + event.getTitle());
            }
            event.setApproved(true);
            eventRepository.save(event);
        }
    }
}

