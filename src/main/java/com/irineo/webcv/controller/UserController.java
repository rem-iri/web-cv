package com.irineo.webcv.controller;

import com.irineo.webcv.model.User;
import com.irineo.webcv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/all")
    List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getById(@PathVariable long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Error("User not found"));

        return user;
    }

    @PostMapping("/user")
    User create(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PutMapping("/user/{id}")
    User update(@PathVariable long id, @RequestBody User newUser) {
        Optional<User> userData = userRepository.findById(id);

        return userData.map(user -> {
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());

            return userRepository.save(user);
        }).orElseThrow(() -> new Error("No user data"));
    }

    @DeleteMapping("/user/{id}")
    void delete(@PathVariable long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}
