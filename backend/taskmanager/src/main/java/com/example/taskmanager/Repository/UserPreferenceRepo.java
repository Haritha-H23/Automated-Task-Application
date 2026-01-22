package com.example.taskmanager.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.Entity.UserPreference;

public interface UserPreferenceRepo extends JpaRepository<UserPreference, Long> {

    Optional<UserPreference> findByUserId(Long id);

    
} 