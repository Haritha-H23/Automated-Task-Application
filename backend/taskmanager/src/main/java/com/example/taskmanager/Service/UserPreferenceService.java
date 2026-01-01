package com.example.taskmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Entity.UserPreference;
import com.example.taskmanager.Repository.UserPreferenceRepo;
import com.example.taskmanager.Repository.UserRepo;

@Service
public class UserPreferenceService {

    @Autowired
    private UserPreferenceRepo preferenceRepo;

    @Autowired
    private UserRepo userRepo;

    private UserPreference createDefaultPreferences(User user) {
        UserPreference pref = new UserPreference();
        pref.setUser(user);
        return preferenceRepo.save(pref);
    }

    public UserPreference getMyPreferences() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return preferenceRepo.findByUserId(user.getId())
            .orElseGet(() -> createDefaultPreferences(user));
    }

    public UserPreference updatePreferences(UserPreference updatedPref) {

        UserPreference existingPref = getMyPreferences();

        existingPref.setEmailNotifications(updatedPref.isEmailNotifications());
        existingPref.setDarkMode(updatedPref.isDarkMode());

        return preferenceRepo.save(existingPref);
    }

}
