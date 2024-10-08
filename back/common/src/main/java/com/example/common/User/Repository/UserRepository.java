package com.example.common.User.Repository;

import com.example.common.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
    Integer countByPointGreaterThan(Integer point);
    Optional<User> findByIdAndEnableTrue(Long id);
}