package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Entity.NotificationLog;
import com.example.email_notifier_backend.Repository.EventsRepository;
import com.example.email_notifier_backend.Repository.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventReminderJob extends QuartzJobBean {

    @Autowired
    private  ReminderService reminderService;
    @Autowired
    private  EmailService emailService;
    @Autowired
    private  NotificationLogRepository notificationLogRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        LocalDate reminderDate = LocalDate.now().plusDays(5);
        List<Events> events = reminderService.getEventsWithRecipients(reminderDate);

        for (Events event : events) {
            if (event.getRecipientEmails() != null) {
                for (String email : event.getRecipientEmails()) {
                    try {
                        String subject = "Reminder: " + event.getTitle();
                        String body = "Event scheduled on " + event.getDate() + " at " + event.getTime() + "\n\nDetails:\n" + event.getDescription();
                        emailService.sendEmail(email, subject, body);

                        NotificationLog log = new NotificationLog();
                        log.setEvent(event);
                        log.setRecipientEmail(email);
                        log.setStatus("SENT");
                        log.setSentTimestamp(LocalDateTime.now());
                        notificationLogRepository.save(log);
                    } catch (Exception e) {
                        NotificationLog log = new NotificationLog();
                        log.setEvent(event);
                        log.setRecipientEmail(email);
                        log.setStatus("FAILED");
                        log.setSentTimestamp(LocalDateTime.now());
                        notificationLogRepository.save(log);
                    }
                }
            }
        }
    }
}

