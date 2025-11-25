package com.example.email_notifier_backend.Controller;

//public class NotificationController {
//
//}
import com.example.email_notifier_backend.Dto.NotificationDTO;
import com.example.email_notifier_backend.Service.NotificationService;
import com.example.email_notifier_backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public List<NotificationDTO> getUserNotifications(Authentication auth) {
        return notificationService.getUserNotifications(auth.getName());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> downloadExcelReport(Authentication auth) {
        String useremail = auth.getName();
        ByteArrayInputStream in = notificationService.generateExcelReportForUser(useremail);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=notifications.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
