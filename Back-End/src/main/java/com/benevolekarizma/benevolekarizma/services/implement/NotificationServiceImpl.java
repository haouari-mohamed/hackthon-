package com.benevolekarizma.benevolekarizma.services.implement;

import com.benevolekarizma.benevolekarizma.DTO.notifications.NotificationRequest;
import com.benevolekarizma.benevolekarizma.DTO.notifications.NotificationResponse;
import com.benevolekarizma.benevolekarizma.models.Notification;
import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.repositories.NotificationRepository;
import com.benevolekarizma.benevolekarizma.repositories.UserRepository;
import com.benevolekarizma.benevolekarizma.services.interfaces.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository personRepository;

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        // Find recipient by ID
        User recipient = personRepository.findById(notificationRequest.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        // Create and save notification
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(notificationRequest.getMessage());
        notification.setRead(false);
        notification.setCreatedAt(new Date(System.currentTimeMillis()));

        Notification savedNotification = notificationRepository.save(notification);

        // Map to NotificationResponse
        return new NotificationResponse(
                savedNotification.getIdNotfication(),
                savedNotification.getRecipient().getIdUser(),
                savedNotification.getMessage(),
                savedNotification.isRead(),
                savedNotification.getCreatedAt()
        );
    }

    @Override
    public List<NotificationResponse> getNotificationsByRecipientId(Long recipientId) {
        // Fetch notifications by recipient ID
        List<Notification> notifications = notificationRepository.findByRecipientId(recipientId);

        // Map to NotificationResponse
        return notifications.stream()
                .map(notification -> new NotificationResponse(
                        notification.getIdNotfication(),
                        notification.getRecipient().getIdUser(),
                        notification.getMessage(),
                        notification.isRead(),
                        notification.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse markAsRead(Long notificationId) {
        // Find notification by ID
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Mark as read
        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);

        // Map to NotificationResponse
        return new NotificationResponse(
                updatedNotification.getIdNotfication(),
                updatedNotification.getRecipient().getIdUser(),
                updatedNotification.getMessage(),
                updatedNotification.isRead(),
                updatedNotification.getCreatedAt()
        );
    }
}
