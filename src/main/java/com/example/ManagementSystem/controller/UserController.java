package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>>getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findByID(@PathVariable("id")UUID id){
        Optional<User> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<User> Create(@Validated @RequestBody User user){
        try {
            User user1 = userService.save(
                    new User(Uuids.timeBased(),user.getFirstname(),user.getLastname(),user.getEmail(),
                    user.getPassword(),user.getConfirmPassword(),user.getRole(),user.getPhoneNumber(),user.getGender(),
                    user.getDateOfBirth(),user.getAddress1(),user.getAddress2(),user.getCity(),user.getState(),user.getZipcode(),user.getFile()));
            return new ResponseEntity<>(user1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id){
        try {
            userService.destroy(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
