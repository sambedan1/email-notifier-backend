package com.example.email_notifier_backend.Dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateEventDTO {
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private Set<String> recipientEmails;
}
