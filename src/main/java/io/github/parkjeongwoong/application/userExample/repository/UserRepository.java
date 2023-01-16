package io.github.parkjeongwoong.application.userExample.repository;

import io.github.parkjeongwoong.entity.example.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
