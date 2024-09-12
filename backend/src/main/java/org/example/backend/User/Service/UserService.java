package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Boolean checkDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public Boolean checkDuplicateNickname(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }
}
