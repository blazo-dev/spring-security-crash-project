package dev.blazo.base.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.blazo.base.entities.Authority;
import dev.blazo.base.utils.AuthorityName;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(AuthorityName name);
}
