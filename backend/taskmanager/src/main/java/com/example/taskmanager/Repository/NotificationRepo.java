package com.example.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.Entity.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
