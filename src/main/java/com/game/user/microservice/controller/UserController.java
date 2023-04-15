package com.game.user.microservice.controller;

import com.game.user.microservice.entities.User;
import com.game.user.microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    // save user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User saved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // get user
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUser(userId));
    }
    // get all users
    @GetMapping
    public ResponseEntity<List<User>> getUser(){
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getAllUsers());
    }
    // update user
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user));
    }
    // delete user
    @DeleteMapping("/{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user_id") String userId ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
    }
}
