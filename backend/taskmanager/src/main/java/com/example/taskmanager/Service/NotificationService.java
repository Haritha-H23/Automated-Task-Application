package com.example.taskmanager.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.Entity.Notification;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.NotificationRepo;
import com.example.taskmanager.Repository.UserRepo;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserRepo userRepo;

    public Notification createNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        return notificationRepo.save(notification);
    }

    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepo.findByUserId(userId);
    }

    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        return notificationRepo.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepo.deleteById(id);
    }
}
