package org.example.backend.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.User.Controller.UserController;
import org.example.backend.User.Service.UserService;
import org.example.backend.File.Service.CloudFileUploadService;
import org.example.backend.Util.JwtUtil;
import org.example.backend.EmailVerify.Service.EmailVerifyService;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Req.UpdateUserPasswordReq;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.example.backend.Common.BaseResponseStatus.USER_INVALID_NICKNAME;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CloudFileUploadService cloudFileUploadService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private EmailVerifyService emailVerifyService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 회원가입_성공() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "password123");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(userService.checkDuplicateEmail(anyString())).thenReturn(true);
        when(userService.checkDuplicateNickname(anyString())).thenReturn(true);
        when(cloudFileUploadService.uploadImg(any(MultipartFile.class))).thenReturn("test-url");

        userController.signup(req, profileImg);

        verify(userService, times(1)).signup(any(), anyString());
        verify(emailVerifyService, times(1)).sendEmail(anyString());
    }

    @Test
    public void 회원가입_중복_이메일() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "password123");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(userService.checkDuplicateEmail(anyString())).thenReturn(false);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_DUPLICATE_EMAIL, exception.getStatus());
    }

    @Test
    public void 회원가입_중복_닉네임() {
        UserSignupReq req = new UserSignupReq("nickname","test@example.com", "password123");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(userService.checkDuplicateEmail(anyString())).thenReturn(true);
        when(userService.checkDuplicateNickname(anyString())).thenReturn(false);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_DUPLICATE_NICKNAME, exception.getStatus());
    }

    @Test
    public void 회원가입_필드_공백() {
        UserSignupReq req = new UserSignupReq("", "password123", "nickname");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_INVALID_INPUT, exception.getStatus());
    }

    @Test
    public void 회원가입_이메일_형식_잘못됨() {
        UserSignupReq req = new UserSignupReq("invalid-email-format", "password123", "nickname");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_INVALID_EMAIL_FORMAT, exception.getStatus());
    }

    @Test
    public void 회원가입_이메일_전송_실패() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "password123");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(userService.checkDuplicateEmail(anyString())).thenReturn(true);
        when(userService.checkDuplicateNickname(anyString())).thenReturn(true);
        when(cloudFileUploadService.uploadImg(any(MultipartFile.class))).thenReturn("test-url");
        doThrow(new RuntimeException()).when(emailVerifyService).sendEmail(anyString());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });

        assertEquals(BaseResponseStatus.USER_EMAIL_SEND_FAILED, exception.getStatus());
    }

    @Test
    public void 회원가입_비밀번호_잘못됨() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "pass ");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_INVALID_PASSWORD, exception.getStatus());
    }

    @Test
    public void 회원가입_프로필이미지_없음() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "password123");

        when(userService.checkDuplicateEmail(anyString())).thenReturn(true);
        when(userService.checkDuplicateNickname(anyString())).thenReturn(true);

        userController.signup(req, null);

        verify(userService, times(1)).signup(any(), eq("https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/11/0d7ca962-ccee-4fbb-9b5d-f5deec5808c6"));
    }

    @Test
    public void 로그아웃_성공() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie expiredCookie = new Cookie("ATOKEN", null);
        when(jwtUtil.removeCookie()).thenReturn(expiredCookie);

        userController.logout(response);

        verify(response, times(1)).addCookie(expiredCookie);
    }

    @Test
    public void 이메일_중복_검사_성공() {
        when(userService.checkDuplicateEmail("test@example.com")).thenReturn(true);

        boolean result = userController.duplicateEmail("test@example.com").getResult();

        assertTrue(result);
        verify(userService, times(1)).checkDuplicateEmail(anyString());
    }

    @Test
    public void 이메일_중복_검사_실패() {
        when(userService.checkDuplicateEmail("test@example.com")).thenReturn(false);

        boolean result = userController.duplicateEmail("test@example.com").getResult();

        assertFalse(result);
        verify(userService, times(1)).checkDuplicateEmail(anyString());
    }

    @Test
    public void 닉네임_수정_성공() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);

        userController.updateNickname(customUserDetails, Map.of("nickname", "newNickname"));

        verify(userService, times(1)).updateNickname(anyLong(), anyString());
    }

    @Test
    public void 닉네임_수정_특수문자_포함() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.updateNickname(customUserDetails, Map.of("nickname", "invalid!nickname"));
        });

        assertEquals(USER_INVALID_NICKNAME, exception.getStatus());
    }

    @Test
    public void 닉네임_수정_닉네임_길이_초과() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.updateNickname(customUserDetails, Map.of("nickname", "a".repeat(46)));
        });

        assertEquals(USER_INVALID_NICKNAME, exception.getStatus());
    }

    @Test
    public void 프로필_이미지_수정_성공() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(cloudFileUploadService.uploadImg(any(MultipartFile.class))).thenReturn("uploaded_url");

        BaseResponse<String> result = userController.updateImg(customUserDetails, profileImg);

        verify(userService, times(1)).updateImg(anyLong(), anyString());
        assertEquals("uploaded_url", result.getResult());
    }

    @Test
    public void 비밀번호_수정_성공() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        UpdateUserPasswordReq req = new UpdateUserPasswordReq("oldPassword", "newPassword", "newPassword");

        BaseResponse<String> result = userController.updatePassword(customUserDetails, req);

        verify(userService, times(1)).updatePassword(anyLong(), any(UpdateUserPasswordReq.class));
        assertNull(result.getResult());
    }

    @Test
    public void 비밀번호_불일치() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        UpdateUserPasswordReq req = new UpdateUserPasswordReq("oldPassword", "newPassword", "differentNewPassword");

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.updatePassword(customUserDetails, req);
        });
        assertEquals(BaseResponseStatus.USER_NEW_PASSWORDS_DO_NOT_MATCH, exception.getStatus());
    }

    @Test
    public void 비밀번호_검증() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(userService.checkPassword(anyLong(), anyString())).thenReturn(true);

        boolean result = userController.checkPassword(customUserDetails, Map.of("password", "testPassword")).getResult();

        assertTrue(result);
    }

    @Test
    public void 비밀번호_검증_실패() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(userService.checkPassword(anyLong(), anyString())).thenReturn(false);

        boolean result = userController.checkPassword(customUserDetails, Map.of("password", "wrongPassword")).getResult();

        assertFalse(result);
        verify(userService, times(1)).checkPassword(anyLong(), eq("wrongPassword"));
    }

    @Test
    public void 회원탈퇴_성공() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        Map<String, String> passwordMap = Map.of("password", "correctPassword");
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(userService.checkPassword(anyLong(), anyString())).thenReturn(true);

        userController.quit(customUserDetails, passwordMap, response);

        verify(userService, times(1)).disableUser(anyLong(), anyString());
        verify(jwtUtil, times(1)).removeCookie();
    }

    @Test
    public void 소셜_회원탈퇴_성공() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        String validJwtToken = "valid_token";
        HttpServletResponse response = mock(HttpServletResponse.class);

        userController.oauthQuit(customUserDetails, validJwtToken, response);

        verify(userService, times(1)).disableSocialUser(anyLong(), eq(validJwtToken));
        verify(jwtUtil, times(1)).removeCookie();
    }

    @Test
    public void 소셜_회원탈퇴_잘못된_토큰() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        String invalidJwtToken = "invalid_token";

        doThrow(new InvalidUserException(BaseResponseStatus.USER_ACCESS_TOKEN_NOT_FOUND)).when(userService)
                .disableSocialUser(anyLong(), eq(invalidJwtToken));

        InvalidUserException exception = assertThrows(InvalidUserException.class, ()-> {
            userController.oauthQuit(customUserDetails, invalidJwtToken, mock(HttpServletResponse.class));
        });
        assertEquals(BaseResponseStatus.USER_ACCESS_TOKEN_NOT_FOUND, exception.getStatus());
    }

}