package com.demo.taskmanager.serviceimpl;

import com.demo.taskmanager.dto.TaskDto;
import com.demo.taskmanager.entity.Task;
import com.demo.taskmanager.exception.ResourceNotFoundException;
import com.demo.taskmanager.repository.TaskRepository;
import com.demo.taskmanager.repository.UserRepository;
import com.demo.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskDto.Response createTask(TaskDto.Request request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus() != null ?
            request.getStatus() : Task.Status.TODO);
        task.setDueDate(request.getDueDate());
        task.setAssignedToId(request.getAssignedToId());
        task.setCreatedById(request.getCreatedById());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskDto.Response getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Task not found with id: " + id));
        return mapToResponse(task);
    }

    @Override
    public List<TaskDto.Response> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto.Response> responses = new ArrayList<>();
        for (Task task : tasks) {
            responses.add(mapToResponse(task));
        }
        return responses;
    }

    @Override
    public TaskDto.Response updateTask(Long id, TaskDto.Request request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Task not found with id: " + id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setAssignedToId(request.getAssignedToId());
        task.setCreatedById(request.getCreatedById());
        task.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskDto.Response updateTaskStatus(Long id,
            TaskDto.StatusUpdate statusUpdate) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Task not found with id: " + id));
        task.setStatus(statusUpdate.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Task not found with id: " + id));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskDto.Response> getTasksByStatus(Task.Status status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        List<TaskDto.Response> responses = new ArrayList<>();
        for (Task task : tasks) {
            responses.add(mapToResponse(task));
        }
        return responses;
    }

    @Override
    public List<TaskDto.Response> getTasksByAssignedTo(Long userId) {
        List<Task> tasks = taskRepository.findByAssignedToId(userId);
        List<TaskDto.Response> responses = new ArrayList<>();
        for (Task task : tasks) {
            responses.add(mapToResponse(task));
        }
        return responses;
    }

    @Override
    public List<TaskDto.Response> getTasksByCreatedBy(Long userId) {
        List<Task> tasks = taskRepository.findByCreatedById(userId);
        List<TaskDto.Response> responses = new ArrayList<>();
        for (Task task : tasks) {
            responses.add(mapToResponse(task));
        }
        return responses;
    }

    private TaskDto.Response mapToResponse(Task task) {
        TaskDto.Response response = new TaskDto.Response();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        if (task.getAssignedToId() != null) {
            userRepository.findById(task.getAssignedToId())
                .ifPresent(user -> {
                    response.setAssignedToId(user.getId());
                    response.setAssignedToName(user.getName());
                });
        }
        if (task.getCreatedById() != null) {
            userRepository.findById(task.getCreatedById())
                .ifPresent(user -> {
                    response.setCreatedById(user.getId());
                    response.setCreatedByName(user.getName());
                });
        }
        return response;
    }
}