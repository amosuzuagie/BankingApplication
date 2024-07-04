package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.dto.UserInfo;
import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import com.mstramohz.BankingApplication.entity.Token;
import com.mstramohz.BankingApplication.repository.AccountUserRepository;
import com.mstramohz.BankingApplication.enums.Role;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountUserService {
    private final MailService mailService;
    private final TokenService tokenService;
    private final BankAccountService accountService;
    private final AccountUserRepository userRepository;

    @Autowired
    public AccountUserService (
            MailService mailService,
            TokenService tokenService,
            BankAccountService accountService,
            AccountUserRepository userRepository
    ) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    public ResponseEntity<List<AccountUser>> getAllAccountUsers () {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<AccountUser> getUserById (long userId) {
        return new ResponseEntity<>(userRepository.findById(userId).get(), HttpStatus.OK);
    }

    public ResponseEntity<AccountUser> getUserByUsername (String username) {
        return new ResponseEntity<>(userRepository.findByUsername(username).get(), HttpStatus.OK);
    }

    public void createUser (UserInfo userInfo) throws MessagingException {
        AccountUser user = new AccountUser();

        user.setFirstname(userInfo.getFirstname());
        user.setLastname(userInfo.getLastname());
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setRole(Role.USER);
        AccountUser savedUser = userRepository.save(user);

        BankAccount account = accountService.openBankAccount(savedUser);

        //generate and send token via mail
        String tokenValue = tokenService.generateToken();
        Token token = tokenService.saveToken(user, tokenValue);
        mailService.registrationNotification(savedUser, tokenValue, account.getAccountNumber());
    }

    public ResponseEntity<AccountUser> updateUser (long id, AccountUser update) {
        AccountUser user = userRepository.findById(id).get();

        user.setFirstname(update.getFirstname());
        user.setLastname(update.getLastname());
        user.setUsername(update.getUsername());
        user.setPhoneNumber(update.getPhoneNumber());

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    public ResponseEntity<AccountUser> deleteUser (long id) {
        AccountUser user = userRepository.findById(id).get();
        userRepository.delete(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
