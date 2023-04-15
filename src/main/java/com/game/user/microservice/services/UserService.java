package com.game.user.microservice.services;

import com.game.user.microservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    User saveUser(User user);

    User getUser(String userId);
    List<User> getAllUsers();

    User updateUser(User user);

    User deleteUser(String id);
}
