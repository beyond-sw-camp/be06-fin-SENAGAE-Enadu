package org.example.backend.Security;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import org.example.backend.User.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws InvalidUserException {
        if (username == null || username.isEmpty()) {
            throw new InvalidUserException(BaseResponseStatus.USER_EMAIL_NULL);
        }
        userService.checkEmailPattern(username);

        User user = userRepository.findByEmail(username)
                .orElse(null);

        return new CustomUserDetails(user);
    }
}
