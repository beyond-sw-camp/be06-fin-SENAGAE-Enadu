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
    public void testSignupSuccess() {
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
    public void testSignupDuplicateEmail() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "password123");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(userService.checkDuplicateEmail(anyString())).thenReturn(false);

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_DUPLICATE_EMAIL, exception.getStatus());
    }

    @Test
    public void testSignupWithDuplicateNickname() {
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
    public void testSignupWithEmptyFields() {
        UserSignupReq req = new UserSignupReq("", "password123", "nickname");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_INVALID_INPUT, exception.getStatus());
    }

    @Test
    public void testSignupWithInvalidEmailFormat() {
        UserSignupReq req = new UserSignupReq("invalid-email-format", "password123", "nickname");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_INVALID_EMAIL_FORMAT, exception.getStatus());
    }

    @Test
    public void testSignupWithInvalidPassword() {
        UserSignupReq req = new UserSignupReq("nickname", "test@example.com", "pass ");
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.signup(req, profileImg);
        });
        assertEquals(BaseResponseStatus.USER_INVALID_PASSWORD, exception.getStatus());
    }

    @Test
    public void testLogout() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie expiredCookie = new Cookie("ATOKEN", null);
        when(jwtUtil.removeCookie()).thenReturn(expiredCookie);

        userController.logout(response);

        verify(response, times(1)).addCookie(expiredCookie);
    }

    @Test
    public void testCheckDuplicateEmailSuccess() {
        when(userService.checkDuplicateEmail("test@example.com")).thenReturn(true);

        boolean result = userController.duplicateEmail("test@example.com").getResult();

        assertTrue(result);
        verify(userService, times(1)).checkDuplicateEmail(anyString());
    }

    @Test
    public void testCheckDuplicateEmailFailure() {
        when(userService.checkDuplicateEmail("test@example.com")).thenReturn(false);

        boolean result = userController.duplicateEmail("test@example.com").getResult();

        assertFalse(result);
        verify(userService, times(1)).checkDuplicateEmail(anyString());
    }

    @Test
    public void testUpdateNickname() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);

        userController.updateNickname(customUserDetails, Map.of("nickname", "newNickname"));

        verify(userService, times(1)).updateNickname(anyLong(), anyString());
    }

    @Test
    public void testUpdateImg() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        MockMultipartFile profileImg = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        when(cloudFileUploadService.uploadImg(any(MultipartFile.class))).thenReturn("uploaded_url");

        BaseResponse<String> result = userController.updateImg(customUserDetails, profileImg);

        verify(userService, times(1)).updateImg(anyLong(), anyString());
        assertEquals("uploaded_url", result.getResult());
    }

    @Test
    public void testUpdatePassword() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        UpdateUserPasswordReq req = new UpdateUserPasswordReq("oldPassword", "newPassword", "newPassword");

        BaseResponse<String> result = userController.updatePassword(customUserDetails, req);

        verify(userService, times(1)).updatePassword(anyLong(), any(UpdateUserPasswordReq.class));
        assertNull(result.getResult());
    }

    @Test
    public void testUpdatePasswordMismatch() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        UpdateUserPasswordReq req = new UpdateUserPasswordReq("oldPassword", "newPassword", "differentNewPassword");

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.updatePassword(customUserDetails, req);
        });
        assertEquals(BaseResponseStatus.USER_NEW_PASSWORDS_DO_NOT_MATCH, exception.getStatus());
    }

    @Test
    public void testCheckPassword() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(userService.checkPassword(anyLong(), anyString())).thenReturn(true);

        boolean result = userController.checkPassword(customUserDetails, Map.of("password", "testPassword")).getResult();

        assertTrue(result);
    }

    @Test
    public void testQuitAccountSuccess() {
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
    public void testOauthQuitSuccess() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        String validJwtToken = "valid_token";
        HttpServletResponse response = mock(HttpServletResponse.class);

        userController.oauthQuit(customUserDetails, validJwtToken, response);

        verify(userService, times(1)).disableSocialUser(anyLong(), eq(validJwtToken));
        verify(jwtUtil, times(1)).removeCookie();
    }

    @Test
    public void testOauthQuitInvalidToken() {
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUserId()).thenReturn(1L);
        String invalidJwtToken = "invalid_token";

        doThrow(new InvalidUserException(BaseResponseStatus.USER_ACCESS_TOKEN_NOT_FOUND)).when(userService)
                .disableSocialUser(anyLong(), eq(invalidJwtToken));

        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            userController.oauthQuit(customUserDetails, invalidJwtToken, mock(HttpServletResponse.class));
        });
        assertEquals(BaseResponseStatus.USER_ACCESS_TOKEN_NOT_FOUND, exception.getStatus());
    }

}