package com.example.taskmanager.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.UserRepo;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepository;

    public List<User> getAllUsersUser(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User newUser) {
        newUser.setId(id);
        return userRepository.save(newUser);
    }

    public String deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        } else {
            return "User Not Found";
        }
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
         return userRepository.findByEmailAndPassword(email, password);
    }
    
}
