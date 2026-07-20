package com.cjpannila.runanalytics.oauth.service;

import com.cjpannila.runanalytics.oauth.util.Constants;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.resend.*;

@Service
public class EmailService {
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    private final String resendApiKey;

    public EmailService(JavaMailSender mailSender, @Value("${resend.api.key}") String resendApiKey) {
        this.mailSender = mailSender;
        this.resendApiKey = resendApiKey;
    }

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

    public void sendAuthorizationCodeEmailViaResend(String code, String scope) throws ResendException {
        Resend resend = new Resend(resendApiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Running Analytics System <onboarding@resend.dev>")
                .to(Constants.EMAIL_ID)
                .subject("Running Analytics System Authorization")
                .html("""
                        <p>A new Running Analytics System authorization was received.</p>
                        <p><strong>Authorization Code:</strong> %s</p>
                        <p><strong>Scope:</strong> %s</p>
                        """.formatted(code, scope))
                .build();
        CreateEmailResponse response = resend.emails().send(params);
        logger.info("Authorization code sent to: {}, responseId: {}", Constants.EMAIL_ID, response.getId());
    }
}