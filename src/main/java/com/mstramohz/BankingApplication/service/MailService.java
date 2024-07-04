package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.entity.AccountUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    @Autowired
    private final JavaMailSender javaMailSender;

    @Async
    public void registrationNotification (AccountUser user, String token, String accountNumber) throws MessagingException {
        String subject = "Registration Notification";
        String message = STR."Dear \{user.getFirstname()},\nCongratulations! Your account is successfully create and your account number is: \{accountNumber}/nKindly click on: http://localhost:8080/api/v1/auth/email_verification/\{token} to verify your email.";
        MimeMessageHelper messageHelper = mimeMessageHelper(user.getUsername(), subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void loginNotification (String receiver, String firstname) throws MessagingException {
        String subject = "Login Notification";
        String message = STR."Dear \{firstname}, \nThere was a successful login to your account. if this wasn't you, please call 077-246810 for prompt action. \n Thank you for banking with us.";
        MimeMessageHelper messageHelper = mimeMessageHelper(receiver, subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void withdrawalNotification (String receiver, String firstname, double amount) throws MessagingException {
        String subject = "Debit Alert";
        String message = STR."Dear \{firstname}, a debit of \{amount} wad diducted from your account.";
        MimeMessageHelper messageHelper = mimeMessageHelper(receiver, subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void depositNotification (String receiver, String firstname, double amount) throws MessagingException {
        String subject = "Debit Alert";
        String message = STR."Dear \{firstname}, a credit of \{amount} was deposited into ypur account.";
        MimeMessageHelper messageHelper = mimeMessageHelper(receiver, subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    private MimeMessageHelper mimeMessageHelper (String receiver, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        messageHelper.setTo(receiver);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        return messageHelper;
    }
}
