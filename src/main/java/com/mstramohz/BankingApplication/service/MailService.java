package com.mstramohz.BankingApplication.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    @Autowired
    private final JavaMailSender javaMailSender;

    public void registrationNotification (String receiver, String firstname, String token) throws MessagingException {
        String subject = "Registration Notification";
        String message = String.format("Dear %s, your account has been created successfully. \nYour token is %s", firstname, token);
        MimeMessageHelper messageHelper = mimeMessageHelper(receiver, subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    public MimeMessageHelper mimeMessageHelper (String receiver, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        messageHelper.setTo(receiver);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        return messageHelper;
    }
}
