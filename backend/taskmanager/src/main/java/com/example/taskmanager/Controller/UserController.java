package com.example.taskmanager.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.UserRepo;
import com.example.taskmanager.Service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired 
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsersUser();
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @GetMapping("getUser/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @PostMapping(
  value = "/uploadProfile",
  consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public User uploadProfile(@RequestPart("image") MultipartFile image) throws IOException {

    String email = SecurityContextHolder.getContext()
            .getAuthentication().getName();

    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    String uploadDir = "uploads/profile/";
    Files.createDirectories(Paths.get(uploadDir));

    String filename = user.getId() + "_" + image.getOriginalFilename();
    Path filePath = Paths.get(uploadDir + filename);
    Files.write(filePath, image.getBytes());

    user.setProfileImage("/uploads/profile/" + filename);
    return userRepo.save(user);
}

    @PutMapping("updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User newUser) {
        return userService.updateUser(id, newUser);
    }
    
    @DeleteMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
