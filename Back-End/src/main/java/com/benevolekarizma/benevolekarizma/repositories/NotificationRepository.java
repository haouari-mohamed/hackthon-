package com.benevolekarizma.benevolekarizma.repositories;

import com.benevolekarizma.benevolekarizma.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.recipient.idUser = :recipientId")
    List<Notification> findByRecipientId(@Param("recipientId") Long recipientId);
}