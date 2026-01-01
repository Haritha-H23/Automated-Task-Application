package com.example.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.Entity.TaskLog;

public interface TaskLogRepo extends JpaRepository<TaskLog, Long> {

    List<TaskLog> findByTaskId(Long taskId);

}