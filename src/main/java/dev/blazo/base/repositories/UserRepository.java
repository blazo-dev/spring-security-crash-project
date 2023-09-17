package dev.blazo.base.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.blazo.base.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
