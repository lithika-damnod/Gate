package com.example.backend.config;

import com.example.backend.models.Account;
import com.example.backend.repository.AccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.List;


@Configurable
@Component
public class AccountConfig {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // TODO: For Testing Purposes Only
    @PostConstruct
    public void initData() {
        Account account1 = new Account(null, "example@example.com", "password1", "firstname1", "lastname1");
        Account account2 = new Account(null, "example@example.com", "password2", "firstname2", "lastname2");
        accountRepository.saveAll(List.of(account1, account2));
    }
}
