package com.example.email_notifier_backend.Controller;

//public class NotificationController {
//
//}
import com.example.email_notifier_backend.Dto.NotificationDTO;
import com.example.email_notifier_backend.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getAllLogs() {
        return notificationService.getAllLogs();
    }

    @GetMapping("/{id}")
    public NotificationDTO getLog(@PathVariable Long id) {
        return notificationService.getLogById(id);
    }
}
