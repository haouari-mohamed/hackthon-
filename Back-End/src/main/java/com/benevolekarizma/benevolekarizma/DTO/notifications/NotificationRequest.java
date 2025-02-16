package com.benevolekarizma.benevolekarizma.DTO.notifications;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationRequest {
    
    private Long recipientId;
    private String message;

}