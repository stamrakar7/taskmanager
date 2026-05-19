package com.demo.taskmanager.dto;

import com.demo.taskmanager.entity.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskDto {

    public static class Request {
        private String title;
        private String description;
        private Task.Priority priority;
        private Task.Status status;
        private LocalDate dueDate;
        private Long assignedToId;
        private Long createdById;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Task.Priority getPriority() { return priority; }
        public Task.Status getStatus() { return status; }
        public LocalDate getDueDate() { return dueDate; }
        public Long getAssignedToId() { return assignedToId; }
        public Long getCreatedById() { return createdById; }

        public void setTitle(String title) { this.title = title; }
        public void setDescription(String d) { this.description = d; }
        public void setPriority(Task.Priority p) { this.priority = p; }
        public void setStatus(Task.Status s) { this.status = s; }
        public void setDueDate(LocalDate d) { this.dueDate = d; }
        public void setAssignedToId(Long id) { this.assignedToId = id; }
        public void setCreatedById(Long id) { this.createdById = id; }
    }

    public static class Response {
        private Long id;
        private String title;
        private String description;
        private Task.Status status;
        private Task.Priority priority;
        private LocalDate dueDate;
        private Long assignedToId;
        private String assignedToName;
        private Long createdById;
        private String createdByName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Task.Status getStatus() { return status; }
        public Task.Priority getPriority() { return priority; }
        public LocalDate getDueDate() { return dueDate; }
        public Long getAssignedToId() { return assignedToId; }
        public String getAssignedToName() { return assignedToName; }
        public Long getCreatedById() { return createdById; }
        public String getCreatedByName() { return createdByName; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }

        public void setId(Long id) { this.id = id; }
        public void setTitle(String title) { this.title = title; }
        public void setDescription(String d) { this.description = d; }
        public void setStatus(Task.Status s) { this.status = s; }
        public void setPriority(Task.Priority p) { this.priority = p; }
        public void setDueDate(LocalDate d) { this.dueDate = d; }
        public void setAssignedToId(Long id) { this.assignedToId = id; }
        public void setAssignedToName(String n) { this.assignedToName = n; }
        public void setCreatedById(Long id) { this.createdById = id; }
        public void setCreatedByName(String n) { this.createdByName = n; }
        public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
        public void setUpdatedAt(LocalDateTime t) { this.updatedAt = t; }
    }

    public static class StatusUpdate {
        private Task.Status status;
        public Task.Status getStatus() { return status; }
        public void setStatus(Task.Status status) { this.status = status; }
    }
}