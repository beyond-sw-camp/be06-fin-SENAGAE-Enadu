package org.example.backend.Security;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws InvalidUserException {
        if (username == null || !username.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_EMAIL_FORMAT);
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
