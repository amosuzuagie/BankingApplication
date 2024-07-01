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
        String message = "Dear " + user.getFirstname() + "," + "\nCongratulations! Your account is successfully create and your account number is: " + accountNumber +
                "/nKindly click on: " + "http://localhost:8080/api/v1/auth/email_verification/" + token + " to verify your email.";
        MimeMessageHelper messageHelper = mimeMessageHelper(user.getUsername(), subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void loginNotification (String receiver, String firstname) throws MessagingException {
        String subject = "Login Notification";
        String message = String.format("Dear %s, \nThere has been a successful login into your Bank Account. If you did not login call 077-242810 for prompt action. \nThank you for banking with us.", firstname);
        MimeMessageHelper messageHelper = mimeMessageHelper(receiver, subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void withdrawalNotification (String receiver, String firstname, double amount) throws MessagingException {
        String subject = "Debit Alert";
        String message = String.format("Dear %s, a debit of %s was debited from your account.", firstname, amount);
        MimeMessageHelper messageHelper = mimeMessageHelper(receiver, subject, message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void depositNotification (String receiver, String firstname, double amount) throws MessagingException {
        String subject = "Debit Alert";
        String message = String.format("Dear %s, a credit of %s was deposited into your account.", firstname, amount);
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
