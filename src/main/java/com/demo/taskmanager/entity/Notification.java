package com.demo.taskmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private boolean isRead;
    private String type;
    private Long userId;
    private LocalDateTime createdAt;

    public Notification() {}

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public String getType() { return type; }
    public Long getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setMessage(String message) { this.message = message; }
    public void setRead(boolean read) { isRead = read; }
    public void setType(String type) { this.type = type; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}