package com.example.taskmanager.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

}