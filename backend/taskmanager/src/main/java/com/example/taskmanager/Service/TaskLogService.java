package com.example.taskmanager.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.TaskLog;
import com.example.taskmanager.Repository.TaskLogRepo;
import com.example.taskmanager.Repository.TaskRepo;

@Service
public class TaskLogService {

    @Autowired
    private TaskLogRepo taskLogRepo;

    @Autowired
    private TaskRepo taskRepo;

    public TaskLog createLog(Long taskId, String action) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskLog log = new TaskLog();
        log.setTask(task);
        log.setAction(action);
        log.setActionTime(LocalDateTime.now());

        return taskLogRepo.save(log);
    }

    public List<TaskLog> getLogsByTask(Long taskId) {
        return taskLogRepo.findByTaskId(taskId);
    }
}
