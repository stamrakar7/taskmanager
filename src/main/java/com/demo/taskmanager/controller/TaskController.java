package com.demo.taskmanager.controller;

import com.demo.taskmanager.dto.TaskDto;
import com.demo.taskmanager.entity.Task;
import com.demo.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto.Response> createTask(
            @RequestBody TaskDto.Request request,
            @RequestHeader("Authorization") String authHeader) {
        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(taskService.createTask(request, token));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto.Response>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto.Response> getTaskById(
            @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto.Response> updateTask(
            @PathVariable Long id,
            @RequestBody TaskDto.Request request) {
        return ResponseEntity.ok(
            taskService.updateTask(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDto.Response> updateStatus(
            @PathVariable Long id,
            @RequestBody TaskDto.StatusUpdate statusUpdate) {
        return ResponseEntity.ok(
            taskService.updateTaskStatus(id, statusUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully!");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDto.Response>> getTasksByStatus(
            @PathVariable Task.Status status) {
        return ResponseEntity.ok(
            taskService.getTasksByStatus(status));
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<TaskDto.Response>> getTasksByAssignedTo(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
            taskService.getTasksByAssignedTo(userId));
    }

    @GetMapping("/created/{userId}")
    public ResponseEntity<List<TaskDto.Response>> getTasksByCreatedBy(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
            taskService.getTasksByCreatedBy(userId));
    }
}