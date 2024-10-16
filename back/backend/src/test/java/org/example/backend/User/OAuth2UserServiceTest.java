package org.example.backend.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.common.User.Repository.UserRepository;
import org.example.backend.Security.CustomOAuth2UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

public class OAuth2UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchEmailFromGitHub() {
        // Given
        String accessToken = "access_token";
        String uri = "https://api.github.com/user/emails";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Map<String, Object>> mockResponse = List.of(
                Map.of("email", "test@example.com", "primary", true, "verified", true)
        );

        ResponseEntity<List<Map<String, Object>>> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<List<Map<String, Object>>>() {})))
                .thenReturn(responseEntity);

        String email = customOAuth2UserService.fetchEmailFromGitHub(accessToken);

        assertEquals("test@example.com", email);
    }

    @Test
    public void testFetchEmailFromGitHub_NoEmailFound() {
        String accessToken = "access_token";
        String uri = "https://api.github.com/user/emails";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> responseEntity = new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<List<Map<String, Object>>>() {})))
                .thenReturn(responseEntity);

        String email = customOAuth2UserService.fetchEmailFromGitHub(accessToken);

        assertNull(email);
    }
}