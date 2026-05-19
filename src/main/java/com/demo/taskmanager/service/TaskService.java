package com.demo.taskmanager.service;

import com.demo.taskmanager.dto.TaskDto;
import com.demo.taskmanager.entity.Task;
import java.util.List;

public interface TaskService {
    TaskDto.Response createTask(TaskDto.Request request);
    TaskDto.Response getTaskById(Long id);
    List<TaskDto.Response> getAllTasks();
    TaskDto.Response updateTask(Long id, TaskDto.Request request);
    TaskDto.Response updateTaskStatus(Long id, TaskDto.StatusUpdate statusUpdate);
    void deleteTask(Long id);
    List<TaskDto.Response> getTasksByStatus(Task.Status status);
    List<TaskDto.Response> getTasksByAssignedTo(Long userId);
    List<TaskDto.Response> getTasksByCreatedBy(Long userId);
}