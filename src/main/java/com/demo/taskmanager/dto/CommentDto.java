package com.demo.taskmanager.dto;

import java.time.LocalDateTime;

public class CommentDto {

    public static class Request {
        private String message;
        private Long taskId;
        private Long userId;

        public String getMessage() { return message; }
        public Long getTaskId() { return taskId; }
        public Long getUserId() { return userId; }

        public void setMessage(String message) { this.message = message; }
        public void setTaskId(Long taskId) { this.taskId = taskId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    public static class Response {
        private Long id;
        private String message;
        private Long taskId;
        private Long userId;
        private LocalDateTime createdAt;

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
}