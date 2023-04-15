package com.game.user.microservice.repositories;

import com.game.user.microservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
