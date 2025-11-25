package com.example.email_notifier_backend.Service;

import com.example.email_notifier_backend.Dto.NotificationDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getUserNotifications(String email);
    ByteArrayInputStream generateExcelReportForUser(String useremail);
}
