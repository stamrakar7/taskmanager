package com.demo.taskmanager.repository;

import com.demo.taskmanager.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// H2 — In-memory database!
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void saveTask_ShouldPersistToH2() {
        Task task = new Task();
        task.setTitle("H2 Test Task");
        task.setStatus(Task.Status.TODO);
        task.setPriority(Task.Priority.HIGH);

        Task saved = taskRepository.save(task);

        assertNotNull(saved.getId());
        assertEquals("H2 Test Task", saved.getTitle());
        System.out.println(
            "H2: Task saved: " + saved.getId());
    }

    @Test
    void findByStatus_ShouldReturnFilteredTasks() {
        Task task1 = new Task();
        task1.setTitle("TODO Task");
        task1.setStatus(Task.Status.TODO);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Done Task");
        task2.setStatus(Task.Status.DONE);
        taskRepository.save(task2);

        List<Task> todoTasks =
            taskRepository.findByStatus(Task.Status.TODO);

        assertEquals(1, todoTasks.size());
        assertEquals("TODO Task",
            todoTasks.get(0).getTitle());
        System.out.println(
            "H2: Filter by status works!");
    }

    @Test
    void findByAssignedToId_ShouldReturnTasks() {
        Task task = new Task();
        task.setTitle("Assigned Task");
        task.setStatus(Task.Status.TODO);
        task.setAssignedToId(1L);
        taskRepository.save(task);

        List<Task> tasks =
            taskRepository.findByAssignedToId(1L);
        assertEquals(1, tasks.size());
        assertFalse(tasks.isEmpty());
        System.out.println(
            "H2: Find by assignee works!");
    }

    @Test
    void findByPriority_ShouldReturnTasks() {
        Task task = new Task();
        task.setTitle("High Priority Task");
        task.setStatus(Task.Status.TODO);
        task.setPriority(Task.Priority.HIGH);
        taskRepository.save(task);

        List<Task> tasks =
            taskRepository.findByPriority(Task.Priority.HIGH);

        assertFalse(tasks.isEmpty());
        System.out.println(
            "H2: Find by priority works!");
    }

    @Test
    void deleteTask_ShouldRemoveFromDB() {
        Task task = new Task();
        task.setTitle("Delete Me");
        task.setStatus(Task.Status.TODO);
        Task saved = taskRepository.save(task);

        taskRepository.delete(saved);

        assertFalse(taskRepository
            .findById(saved.getId()).isPresent());
        System.out.println(
            "H2: Task deleted from DB!");
    }
}