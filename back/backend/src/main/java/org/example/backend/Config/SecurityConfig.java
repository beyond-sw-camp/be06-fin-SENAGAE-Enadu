package org.example.backend.Config;

import lombok.RequiredArgsConstructor;
import org.example.backend.Config.Filter.JwtFilter;
import org.example.backend.Config.Filter.LoginFilter;
import org.example.backend.Exception.CustomAuthenticationEntryPoint;
import org.example.backend.Exception.CustomAuthenticationFailureHandler;
import org.example.backend.Security.OAuth2LoginFailureHandler;
import org.example.backend.Security.OAuth2LoginSuccessHandler;
import org.example.backend.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Value("${frontend.url}")
    private String FRONT_URL;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음
        );
        http.csrf(csrf -> csrf.disable());
        http.httpBasic(basic -> basic.disable());
        http.formLogin((formLogin) ->
                formLogin
                        .loginPage("/login")
                        .usernameParameter("email") // 기본 username을 email로 변경
                        .passwordParameter("password")
                        .permitAll()
        );

        // GitHub 로그인 추가
        http.oauth2Login(oauth2 ->
                oauth2
                        .loginPage("/login")
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .permitAll()
        );

        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/email/verify").permitAll()
                        .requestMatchers("/point/ranking/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/mypage/log/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/**").authenticated()
                        .requestMatchers("/chat/**").authenticated()
                        .requestMatchers("/point/**").authenticated()
                        .requestMatchers("/mypage/**").authenticated()
                        .anyRequest().permitAll()
        );

        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)); // 인가되지 않은 사용자가 요청을 보냈을 때 처리하는 handler

        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        LoginFilter loginFilter = new LoginFilter(jwtUtil, authenticationManager);
        loginFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(FRONT_URL);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
