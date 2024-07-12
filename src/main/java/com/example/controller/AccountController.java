package com.example.controller;

import javax.naming.AuthenticationException;

import org.h2.security.auth.AuthConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    

    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account existingAccount = accountService.findByUsername(account);
        if (existingAccount != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account savedAccount = accountService.registerAccount(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) throws AuthenticationException {
        Account loggedInAccount = accountService.loginAccount(account);
        if (loggedInAccount != null) {
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
    }

}
