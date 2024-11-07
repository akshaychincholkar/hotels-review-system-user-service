package com.game.user.microservice.services.impl;

import com.game.user.microservice.entities.User;
import com.game.user.microservice.exceptions.UserNotFoundException;
import com.game.user.microservice.repositories.UserRepository;
import com.game.user.microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        String uid = UUID.randomUUID().toString();
        user.setId(uid);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        // Get the ratings
        ArrayList ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getId(),ArrayList.class);
        user.setRatings(ratings);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        userRepository.deleteById(id);
        return user;
    }
}
