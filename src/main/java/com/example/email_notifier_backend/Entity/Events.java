package com.example.email_notifier_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private boolean approved; // For admin or workflow

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // event creator

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_recipients", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "email")
    private Set<String> recipientEmails;

    @OneToMany(mappedBy = "event")
    private Set<NotificationLog> notifications;
}