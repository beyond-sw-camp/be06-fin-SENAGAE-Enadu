package com.example.common.User.Repository;

import com.example.common.User.Model.Entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findByNicknameAndEnableTrue(String nickname);
    Optional<User> findByEmail(String email);
    Integer countByPointGreaterThanAndEnableTrue(Integer point);

    Optional<User> findByIdAndEnableTrue(Long id);

    List<User> findByEnableTrueAndIdNot(Long id, Sort sort);

}