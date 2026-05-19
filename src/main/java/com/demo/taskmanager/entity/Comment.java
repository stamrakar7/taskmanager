package com.demo.taskmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long taskId;
    private Long userId;
    private LocalDateTime createdAt;

    public Comment() {}

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public Long getTaskId() { return taskId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setMessage(String message) { this.message = message; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}