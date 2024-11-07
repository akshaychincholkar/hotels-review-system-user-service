package com.game.user.microservice.entities;

import com.game.reviews.microservice.entities.Rating;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false)
    private String id;
    private String name;
    private String email;
    private String about;
    @Transient
    private List<Rating> ratings;
}
