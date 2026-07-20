package com.cjpannila.runanalytics.oauth.service;

import com.cjpannila.runanalytics.oauth.util.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public void sendAuthorizationCode(String code, String scope) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(Constants.EMAIL_ID);
        message.setSubject("Running Analytics System Authorization");
        message.setText("""
                A new Running Analytics System authorization was received.

                Authorization Code:
                %s

                Scope:
                %s
                """.formatted(code, scope));
        mailSender.send(message);
        logger.info("Authorization code sent to: " + Constants.EMAIL_ID);
    }
}