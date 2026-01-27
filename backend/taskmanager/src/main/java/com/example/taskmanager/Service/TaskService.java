package com.example.taskmanager.Service;

import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.TaskRepo;
import com.example.taskmanager.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            task.getStatus() != null &&
            !existingTask.getStatus().equals(task.getStatus());

    if (task.getTitle() != null)
        existingTask.setTitle(task.getTitle());

    if (task.getDescription() != null)
        existingTask.setDescription(task.getDescription());

    if (task.getPriority() != null)
        existingTask.setPriority(task.getPriority());

    if (task.getDueDate() != null)
        existingTask.setDueDate(task.getDueDate());

    if (task.getStatus() != null)
        existingTask.setStatus(task.getStatus());

    Task updatedTask = taskRepository.save(existingTask);

    taskLogService.createLog(id, "UPDATED");


    if (statusChanged) {

        taskLogService.createLog(id, "STATUS_CHANGED");

        if ("COMPLETED".equalsIgnoreCase(existingTask.getStatus())) {
            taskLogService.createLog(id, "COMPLETED");

            notificationService.createNotification(
               existingTask.getUser(),
               "COMPLETED",
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

        taskRepository.delete(task);
    }
    @Scheduled(cron = "0 30 9 * * *")
public void notifyOverdueTasks() {

    LocalDate today = LocalDate.now();

    List<Task> overdueTasks =
            taskRepository.findByDueDateBeforeAndStatus(today, "PENDING");

    for (Task task : overdueTasks) {
        notificationService.createNotification(
                task.getUser(),
                "OVERDUE",
                "Task overdue: " + task.getTitle()
        );
    }
}
    @Scheduled(cron = "0 0 8 * * *")
public void sendDailySummary() {

    List<User> users = userRepo.findAll();

    for (User user : users) {

        long pending =
                taskRepository.countByUserAndStatus(user, "PENDING");

        long completed =
                taskRepository.countByUserAndStatus(user, "COMPLETED");

        long overdue =
                taskRepository.countByUserAndDueDateBeforeAndStatus(
                        user, LocalDate.now(), "PENDING"
                );

        String message = String.format(
                "Daily Summary: %d pending, %d completed, %d overdue",
                pending, completed, overdue
        );

        notificationService.createNotification(
                user,
                "DAILY_SUMMARY",
                message
        );
    }
}


}
