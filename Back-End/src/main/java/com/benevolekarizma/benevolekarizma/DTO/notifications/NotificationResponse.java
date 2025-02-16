package com.benevolekarizma.benevolekarizma.DTO.notifications;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private Long recipientId;
    private String message;
    private boolean isRead;
    private Date createdAt;

}
