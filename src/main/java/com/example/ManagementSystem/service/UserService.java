package com.example.ManagementSystem.service;

import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User>findAll(){
        return userRepository.findAll();
    }
    public Optional<User>findById(UUID id){
       return userRepository.findById(id);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public void destroy(UUID id){
        userRepository.deleteById(id);
    }
}
