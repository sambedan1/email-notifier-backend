package com.example.email_notifier_backend.Repository;

import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    // All logs for a specific recipient (email)
    List<NotificationLog> findByRecipientEmail(String recipientEmail);

    // For event-based searches
    List<NotificationLog> findByEvent_Id(Long eventId);
}
