package org.example.backend.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import org.example.backend.User.Model.Req.UpdateUserPasswordReq;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.example.backend.User.Service.UserService;
import org.example.backend.Util.JwtUtil;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(userService, "CLIENT_ID", "test-client-id");
        ReflectionTestUtils.setField(userService, "CLIENT_SECRET", "test-client-secret");
    }

    @Test
    public void 회원가입_성공() {
        UserSignupReq req = new UserSignupReq("test@example.com", "password", "nickname");
        User user = User.builder()
                .email(req.getEmail())
                .password("encoded_password")
                .nickname(req.getNickname())
                .build();

        when(passwordEncoder.encode(req.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.signup(req, "profile_img");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void 이메일_중복확인_성공() {

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        boolean result = userService.checkDuplicateEmail("test@example.com");

        assertTrue(result);
    }

    @Test
    public void 이메일_중복확인_실패() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        boolean result = userService.checkDuplicateEmail("test@example.com");

        assertFalse(result);
    }

    @Test
    public void 닉네임_중복확인_성공() {

        when(userRepository.findByNickname("nickname")).thenReturn(Optional.empty());

        boolean result = userService.checkDuplicateNickname("nickname");

        assertTrue(result);
    }

    @Test
    public void 닉네임_중복확인_실패() {
        when(userRepository.findByNickname("nickname")).thenReturn(Optional.of(new User()));

        boolean result = userService.checkDuplicateNickname("nickname");

        assertFalse(result);
    }

    @Test
    public void 닉네임변경_성공() {
        User user = User.builder().id(1L).nickname("old_nickname").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByNickname("new_nickname")).thenReturn(Optional.empty());

        userService.updateNickname(1L, "new_nickname");

        verify(userRepository, times(1)).save(user);
        assertEquals("new_nickname", user.getNickname());
    }

    @Test
    public void 닉네임변경_실패() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userService.updateNickname(1L, "new_nickname");
        });

        assertEquals(BaseResponseStatus.USER_NOT_FOUND, exception.getStatus());
    }

    @Test
    public void 비밀번호변경_성공() {
        User user = User.builder().password("encoded_old_password").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old_password", user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("new_password")).thenReturn("encoded_new_password");

        UpdateUserPasswordReq req = new UpdateUserPasswordReq("old_password", "new_password","new_password");

        userService.updatePassword(1L, req);

        verify(userRepository, times(1)).save(user);
        assertEquals("encoded_new_password", user.getPassword());
    }

    @Test
    public void 비밀번호변경_실패() {
        User user = User.builder().password("encoded_old_password").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_old_password", user.getPassword())).thenReturn(false);

        UpdateUserPasswordReq req = new UpdateUserPasswordReq("wrong_old_password", "new_password","new_password");

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userService.updatePassword(1L, req);
        });

        assertEquals(BaseResponseStatus.USER_PASSWORDS_DO_NOT_MATCH, exception.getStatus());
    }

    @Test
    public void 프로필이미지_변경() {
        User user = User.builder().id(1L).profileImg("old_img").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateImg(1L, "new_img");

        verify(userRepository, times(1)).save(user);
        assertEquals("new_img", user.getProfileImg());
    }

    @Test
    public void 유저비활성화_성공() {
        User user = User.builder().id(1L).password("encoded_password").enable(true).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        userService.disableUser(1L, "password");

        verify(userRepository, times(1)).save(user);
        assertFalse(user.getEnable());
    }

    @Test
    public void 유저비활성화_실패() {
        User user = User.builder().id(1L).password("encoded_password").enable(true).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_password", user.getPassword())).thenReturn(false);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userService.disableUser(1L, "wrong_password");
        });

        assertEquals(BaseResponseStatus.USER_PASSWORDS_DO_NOT_MATCH, exception.getStatus());
    }

    @Test
    public void 이메일_인증상태_업데이트_성공() {
        User user = User.builder().email("test@example.com").verify(false).build();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        userService.updateVerifiedStatus("test@example.com");

        verify(userRepository, times(1)).save(user);
        assertTrue(user.getVerify());
    }

    @Test
    public void 이메일_인증상태_업데이트_실패() {
        when(userRepository.findByEmail("non_existent@example.com")).thenReturn(Optional.empty());

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.updateVerifiedStatus("non_existent@example.com");
        });

        assertEquals(BaseResponseStatus.EMAIL_VERIFY_FAIL, exception.getStatus());
    }

    @Test
    public void 소셜유저비활성화_성공() {
        User user = User.builder().id(1L).enable(true).build();
        when(userRepository.findByIdAndEnableTrue(1L)).thenReturn(Optional.of(user));
        when(jwtUtil.getAccessToken("valid_token")).thenReturn("access_token");

        ResponseEntity<Object> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Object.class))
        ).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        userService.disableSocialUser(1L, "valid_token");

        verify(userRepository, times(1)).save(user);
        assertFalse(user.getEnable());
    }

    @Test
    public void 소셜유저비활성화_실패_토큰없음() {
        User user = User.builder().id(1L).enable(true).build();
        when(userRepository.findByIdAndEnableTrue(1L)).thenReturn(Optional.of(user));
        when(jwtUtil.getAccessToken("invalid_token")).thenReturn(null);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userService.disableSocialUser(1L, "invalid_token");
        });

        assertEquals(BaseResponseStatus.USER_ACCESS_TOKEN_NOT_FOUND, exception.getStatus());
    }
}