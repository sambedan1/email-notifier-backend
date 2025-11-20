package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getAllLogs();

    NotificationDTO getLogById(Long id);
}
