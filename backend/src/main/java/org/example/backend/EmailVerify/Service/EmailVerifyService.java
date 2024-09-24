package org.example.backend.EmailVerify.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Common.GlobalMessage;
import org.example.backend.EmailVerify.Model.Entity.EmailVerify;
import org.example.backend.EmailVerify.Repository.EmailVerifyRepository;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.example.backend.User.Service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final EmailVerifyRepository emailVerifyRepository;
    private final JavaMailSender javaMailSender;
    private final UserService userService;


    public void sendEmail(String email) {
        String uuid = UUID.randomUUID().toString();

        // 이메일이 이미 존재하는지 확인
        EmailVerify emailVerify = emailVerifyRepository.findByEmail(email)
                .map(existing -> {
                    existing.updateUuid(uuid); // UUID 업데이트
                    return emailVerifyRepository.save(existing); // 업데이트된 레코드 저장
                })
                .orElseGet(() -> {
                    // 존재하지 않으면 새로운 EmailVerify 객체 생성
                    EmailVerify newEmailVerify = EmailVerify.builder()
                            .email(email)
                            .uuid(uuid)
                            .build();
                    return emailVerifyRepository.save(newEmailVerify); // 새 레코드 저장
                });

        // 인증 링크 생성 (UUID 포함)
        String verificationLink = "http://localhost:8081/email/verify?email=" + email + "&uuid=" + uuid;

        // 이메일 전송 설정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(GlobalMessage.EMAIL_TITLE.getMessage());
        message.setText(verificationLink);
        javaMailSender.send(message); // 이메일 전송
    }


    public void verifyEmail(String email, String uuid) {
        EmailVerify existingEmailVerify = emailVerifyRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL));

        // 기존 UUID와 비교하여 인증을 시도
        if (!existingEmailVerify.getUuid().equals(uuid)) {
            throw new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL); // UUID 불일치
        }

        // UUID가 일치할 경우 verified 상태 업데이트
        userService.updateVerifiedStatus(email);
    }
}