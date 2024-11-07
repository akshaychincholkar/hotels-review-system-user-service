package com.game.user.microservice.controller;

import com.game.user.microservice.entities.User;
import com.game.user.microservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // save user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User saved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    int retryCount = 1;
    // get user
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "getUser_UserService",fallbackMethod = "getUserFallback")
//    @Retry(name = "getUser_UserService", fallbackMethod = "getUserFallback")
    @RateLimiter(name = "user_rate_limiter",fallbackMethod = "getUserFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        logger.info("Attempting retry {}.. ",retryCount);
        retryCount++;
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUser(userId));
    }
    public ResponseEntity<User> getUserFallback(String userId,Exception ex){
        logger.info("User fallback invoked: "+ex.getMessage());
        User user = User.builder().email("Dummy@gmail.com").name("Dummy").about("This dummy user is sent as any service is not working").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
    }
    // get all users
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
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
