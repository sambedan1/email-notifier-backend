package com.example.email_notifier_backend.Repository;

import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    Optional<NotificationLog> findTopByEventOrderByIdDesc(Events event);
}
