package com.demo.taskmanager.dto;

import java.time.LocalDateTime;

public class NotificationDto {

    public static class Request {
        private String message;
        private String type;
        private Long userId;

        public String getMessage() { return message; }
        public String getType() { return type; }
        public Long getUserId() { return userId; }

        public void setMessage(String message) { this.message = message; }
        public void setType(String type) { this.type = type; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    public static class Response {
        private Long id;
        private String message;
        private boolean isRead;
        private String type;
        private Long userId;
        private LocalDateTime createdAt;

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
}