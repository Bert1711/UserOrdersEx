package com.zaroyan.userordersapi.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.zaroyan.userordersapi.model.User;
import com.zaroyan.userordersapi.servicies.UserService;
import com.zaroyan.userordersapi.util.Views;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Zaroyan
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/summary")
    @JsonView(Views.UserSummary.class)
    public List<User> getAllUsersSummary() {
        return userService.findAllUsers();
    }

    @GetMapping("/details")
    @JsonView(Views.UserDetails.class)
    public List<User> getAllUsersDetails() {
        return userService.findAllUsers();
    }




    @GetMapping("/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
