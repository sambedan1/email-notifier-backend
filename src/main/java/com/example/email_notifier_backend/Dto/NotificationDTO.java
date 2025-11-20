//package com.example.email_notifier_backend.Dto;
//
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Data
//public class NotificationDTO {
//    private Long id;
//    private String email;
//    private String subject;
//    private String status;
//    private LocalDateTime sentAt;
//}
package com.example.email_notifier_backend.Dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String email;
    private String subject;
    private String status;
    private LocalDateTime sentAt;
}

