package com.example.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername().isBlank() || account == null) {
            return null;
        }
        if ((account.getPassword().length() < 4)) {
            return null;
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return null;
        }
        account.setPassword(account.getPassword());
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Account loggedInAccount = accountRepository.findByUsername(account.getUsername());
        if ((loggedInAccount != null) && (account.getPassword().equals(loggedInAccount.getPassword()))) {
            return loggedInAccount;
        }
        return null;
    }

    public Account findByUsername(Account account) {
        return accountRepository.findByUsername(account.getUsername());
    }

    


}
