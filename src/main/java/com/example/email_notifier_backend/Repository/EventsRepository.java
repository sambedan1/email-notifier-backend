package com.example.email_notifier_backend.Repository;

import com.example.email_notifier_backend.Entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
//@Repository
//public interface EventsRepository extends JpaRepository<Events, Long> {
//    List<Events> findByUserId(Long userId);
//    List<Events> findByDate(LocalDate date);
//    List<Events> findByDateAndApprovedTrueAndUserEmailSentFalse(LocalDate date);
//    List<Events> findAllByEmailSentTrue();
//    @Query("SELECT e FROM Events e WHERE e.date = :date AND e.approved = true AND e.emailSent = false")
//    List<Events> findEventsForReminder(@Param("date") LocalDate date);
//
//}

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    List<Events> findByUserId(Long userId);

    List<Events> findByDate(LocalDate date);

    List<Events> findByDateAndApprovedTrueAndEmailSentFalse(LocalDate date);

    List<Events> findAllByEmailSentTrue();

    @Query("SELECT e FROM Events e WHERE e.date = :date AND e.approved = true AND e.emailSent = false")
    List<Events> findEventsForReminder(@Param("date") LocalDate date);
}
