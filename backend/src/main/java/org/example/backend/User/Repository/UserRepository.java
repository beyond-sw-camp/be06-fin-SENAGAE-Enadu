package org.example.backend.User.Repository;

import org.example.backend.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);
    Integer countByPointGreaterThan(Integer point);

}