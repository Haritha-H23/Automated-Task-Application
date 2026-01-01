package com.example.taskmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.Entity.UserPreference;
import com.example.taskmanager.Service.UserPreferenceService;

@RestController
@RequestMapping("/api/preferences")
@CrossOrigin
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService preferenceService;

    @GetMapping
    public UserPreference getMyPreferences() {
        return preferenceService.getMyPreferences();
    }

    @PutMapping
    public UserPreference updatePreferences(@RequestBody UserPreference preference) {
        return preferenceService.updatePreferences(preference);
    }
}
