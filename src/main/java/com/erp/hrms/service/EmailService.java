package com.erp.hrms.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public String sendAuthCode(String email){

        String authCode = String.valueOf((int)(Math.random()*900000)+100000);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("HRMS 이메일 인증");
        message.setText("인증번호 : " + authCode);

        mailSender.send(message);

        return authCode;
    }

    public void sendTempPassword(String email, String tempPassword){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("HRMS 임시 비밀번호");
        message.setText("임시 비밀번호 : " + tempPassword);

        mailSender.send(message);
    }
}
