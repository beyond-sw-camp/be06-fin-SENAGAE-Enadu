package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(UserSignupReq userSignupReq, String profileImg ) {
        User user = User.builder()
                .email(userSignupReq.getEmail())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .profileImg(profileImg)
                .password(passwordEncoder.encode(userSignupReq.getPassword()))
                .nickname(userSignupReq.getNickname())
                .build();
        userRepository.save(user);
    }

    public Boolean checkDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public Boolean checkDuplicateNickname(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }
}
