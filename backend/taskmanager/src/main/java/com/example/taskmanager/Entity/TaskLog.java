package com.example.taskmanager.Entity;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "task_logs")
@Data
public class TaskLog {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String action;
    private LocalDateTime actionTime=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="task_id", nullable=false)
    private Task task;
}
