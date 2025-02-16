package com.benevolekarizma.benevolekarizma.services.interfaces;

import com.benevolekarizma.benevolekarizma.DTO.notifications.NotificationRequest;
import com.benevolekarizma.benevolekarizma.DTO.notifications.NotificationResponse;
import java.util.List;

public interface NotificationService {
    NotificationResponse sendNotification(NotificationRequest notificationRequest);
    List<NotificationResponse> getNotificationsByRecipientId(Long recipientId);
    NotificationResponse markAsRead(Long notificationId);
}