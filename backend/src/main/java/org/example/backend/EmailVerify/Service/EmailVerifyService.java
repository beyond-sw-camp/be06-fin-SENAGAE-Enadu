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

    public void verifyEmail(@RequestParam String email,@RequestParam String uuid){
        List<EmailVerify> emailVerifies = emailVerifyRepository.findAllByEmail(email);
        if (emailVerifies.isEmpty()) {
            throw new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL);
        }

        for (EmailVerify emailVerify : emailVerifies) {
            if (emailVerify.getUuid().equals(uuid)) {
                userService.updateVerifiedStatus(email);
                return;
            }
        }

        throw new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL);
    }

}
