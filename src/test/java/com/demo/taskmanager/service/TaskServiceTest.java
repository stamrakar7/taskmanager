package com.demo.taskmanager.service;

import com.demo.taskmanager.dto.TaskDto;
import com.demo.taskmanager.entity.Task;
import com.demo.taskmanager.exception.ResourceNotFoundException;
import com.demo.taskmanager.repository.TaskRepository;
import com.demo.taskmanager.repository.UserRepository;
import com.demo.taskmanager.serviceimpl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// MOCKITO — Mocks repository, no real DB!
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto.Request taskRequest;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setStatus(Task.Status.TODO);
        task.setPriority(Task.Priority.HIGH);

        taskRequest = new TaskDto.Request();
        taskRequest.setTitle("Test Task");
        taskRequest.setStatus(Task.Status.TODO);
        taskRequest.setPriority(Task.Priority.HIGH);
    }

    @Test
    void createTask_ShouldReturnTask() {
        when(taskRepository.save(any(Task.class)))
            .thenReturn(task);

        TaskDto.Response response =
            taskService.createTask(taskRequest,null);

        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        verify(taskRepository, times(1))
            .save(any(Task.class));
        System.out.println("MOCKITO: Task created!");
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        when(taskRepository.findById(1L))
            .thenReturn(Optional.of(task));

        TaskDto.Response response =
            taskService.getTaskById(1L);

        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        System.out.println("MOCKITO: Task found!");
    }

    @Test
    void getTaskById_ShouldThrow_WhenNotFound() {
        when(taskRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> taskService.getTaskById(99L));
        System.out.println(
            "MOCKITO: Exception thrown correctly!");
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        when(taskRepository.findAll())
            .thenReturn(Arrays.asList(task));

        List<TaskDto.Response> responses =
            taskService.getAllTasks();

        assertEquals(1, responses.size());
        System.out.println(
            "MOCKITO: All tasks returned!");
    }

    @Test
    void deleteTask_ShouldCallRepository() {
        when(taskRepository.findById(1L))
            .thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
        System.out.println("MOCKITO: Task deleted!");
    }

    @Test
    void updateTaskStatus_ShouldUpdateStatus() {
        when(taskRepository.findById(1L))
            .thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class)))
            .thenReturn(task);

        TaskDto.StatusUpdate statusUpdate =
            new TaskDto.StatusUpdate();
        statusUpdate.setStatus(Task.Status.DONE);

        TaskDto.Response response =
            taskService.updateTaskStatus(1L, statusUpdate);

        assertNotNull(response);
        verify(taskRepository, times(1))
            .save(any(Task.class));
        System.out.println(
            "MOCKITO: Status updated to DONE!");
    }
}