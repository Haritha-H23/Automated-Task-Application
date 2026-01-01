package com.example.taskmanager.Service;

import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.TaskRepo;
import com.example.taskmanager.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepository;

    @Autowired
    private TaskLogService taskLogService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepo userRepo;

    public Task createTask(Task task) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        task.setStatus("PENDING");

        Task savedTask = taskRepository.save(task);

        taskLogService.createLog(savedTask.getId(), "CREATED");

        notificationService.createNotification(
                user,
                "New task created: " + savedTask.getTitle()
        );

        return savedTask;
    }

    public List<Task> getAllTasks() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return taskRepository.findByUserEmail(email);
    }

    public Task updateTask(Long id, Task task) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        boolean statusChanged =
                !existingTask.getStatus().equals(task.getStatus());

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());

        Task updatedTask = taskRepository.save(existingTask);

        taskLogService.createLog(id, "UPDATED");

        notificationService.createNotification(
                existingTask.getUser(),
                "Task updated: " + existingTask.getTitle()
        );

        if (statusChanged) {

            taskLogService.createLog(id, "STATUS_CHANGED");

            notificationService.createNotification(
                    existingTask.getUser(),
                    "Task status changed to: " + task.getStatus()
            );

            if ("COMPLETED".equalsIgnoreCase(task.getStatus())) {
                taskLogService.createLog(id, "COMPLETED");

                notificationService.createNotification(
                        existingTask.getUser(),
                        "Task completed: " + existingTask.getTitle()
                );
            }
        }

        return updatedTask;
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskLogService.createLog(id, "DELETED");

        notificationService.createNotification(
                task.getUser(),
                "Task deleted: " + task.getTitle()
        );

        taskRepository.delete(task);
    }
}
