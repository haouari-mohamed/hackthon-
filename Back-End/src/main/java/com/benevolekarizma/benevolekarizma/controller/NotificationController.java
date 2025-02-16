package com.benevolekarizma.benevolekarizma.controller;

import com.benevolekarizma.benevolekarizma.DTO.notifications.NotificationRequest;
import com.benevolekarizma.benevolekarizma.DTO.notifications.NotificationResponse;
import com.benevolekarizma.benevolekarizma.services.interfaces.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse sendNotification(@RequestBody NotificationRequest notificationRequest) {
        return notificationService.sendNotification(notificationRequest);
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationResponse> getNotificationsByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId);
    }

    @PutMapping("/{notificationId}/mark-as-read")
    public NotificationResponse markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }
}