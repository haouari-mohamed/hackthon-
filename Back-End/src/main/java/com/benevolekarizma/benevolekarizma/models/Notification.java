package com.benevolekarizma.benevolekarizma.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotfication;

    @ManyToOne
    private User recipient;

    private String message;
    
    private boolean isRead;
    private Date createdAt;
}