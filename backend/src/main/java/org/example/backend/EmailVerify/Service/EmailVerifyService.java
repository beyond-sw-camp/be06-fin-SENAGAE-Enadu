package org.example.backend.EmailVerify.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Common.GlobalMessage;
import org.example.backend.EmailVerify.Model.Entity.EmailVerify;
import org.example.backend.EmailVerify.Repository.EmailVerifyRepository;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.example.backend.Exception.custom.InvalidErrorBoardException;
import org.example.backend.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final EmailVerifyRepository emailVerifyRepository;
    private final JavaMailSender javaMailSender;
    private final UserService userService;


    public void sendEmail(String email){
        String uuid = UUID.randomUUID().toString();
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();
       emailVerifyRepository.save(emailVerify);
        // 이메일 전송 설정
       SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(GlobalMessage.EMAIL_TITLE.getMessage());
        message.setText(GlobalMessage.EMAIL_URL_TEMPLATE.formatUrl(email,uuid));
        javaMailSender.send(message); // 이메일 전송
    }

    public void verifyEmail(String email, String uuid) {
        EmailVerify existingEmailVerify = emailVerifyRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL));

        // 이전 uuid와 비교하여 인증할 수 없는 경우 처리
        if (existingEmailVerify.getUuid().equals(uuid)) {
            throw new InvalidEmailException(BaseResponseStatus.UUID_ALREADY_USED);
        }

        // UUID 업데이트
        existingEmailVerify.updateUuid(uuid);
        emailVerifyRepository.save(existingEmailVerify);

        // UUID 검증 로직: 이메일이 존재하고 UUID가 일치하면 verified 상태 업데이트
        if (existingEmailVerify.getUuid().equals(uuid)) {
            userService.updateVerifiedStatus(email);
        } else {
            throw new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL);
        }
    }

}
