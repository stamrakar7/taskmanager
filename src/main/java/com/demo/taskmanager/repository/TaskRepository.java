

package com.demo.taskmanager.repository;

import com.demo.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Task.Status status);
    List<Task> findByPriority(Task.Priority priority);
    List<Task> findByAssignedToId(Long assignedToId);
    List<Task> findByCreatedById(Long createdById);
    
    /*@Query("SELECT t FROM Task t WHERE t.dueDate <= :date AND t.status != 'DONE'")
    List<Task> findOverdueTasks(LocalDate date);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.status = :status")
    List<Task> findByAssignedToIdAndStatus(Long userId, Task.Status status);*/
}
