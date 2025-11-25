package com.example.email_notifier_backend.Repository;

import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Entity.User;
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
    List<Events> findByUser(User user);
    List<Events> findByDate(LocalDate date);

    @Query("SELECT e FROM Events e JOIN FETCH e.user WHERE e.user.email = :email")
    List<Events> findByUserEmailWithUser(@Param("email") String email);

    @Query("SELECT DISTINCT e FROM Events e LEFT JOIN FETCH e.recipientEmails WHERE e.date = :date")
    List<Events> findByDateWithRecipients(@Param("date") LocalDate date);


}
