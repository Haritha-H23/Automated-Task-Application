package com.example.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.Entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUserEmail(String email);

    
}
