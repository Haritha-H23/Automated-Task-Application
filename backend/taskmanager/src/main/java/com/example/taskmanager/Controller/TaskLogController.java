package com.example.taskmanager.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.Entity.TaskLog;
import com.example.taskmanager.Service.TaskLogService;

@RestController
@RequestMapping("/api/task-logs")
@CrossOrigin
public class TaskLogController {

    @Autowired
    private TaskLogService taskLogService;

    @GetMapping("/task/{taskId}")
    public List<TaskLog> getLogsByTask(@PathVariable Long taskId) {
        return taskLogService.getLogsByTask(taskId);
    }
}
