package com.example.taskmanager.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUserEmail(String email);

    List<Task> findByDueDateBeforeAndStatus(LocalDate today, String string);

    long countByUserAndStatus(User user, String string);

    long countByUserAndDueDateBeforeAndStatus(User user, LocalDate now, String string);

    
}
