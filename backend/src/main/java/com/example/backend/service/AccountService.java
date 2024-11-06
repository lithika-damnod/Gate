package com.example.backend.service;

import com.example.backend.models.Account;
import com.example.backend.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    // repositories
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccount() {
        return accountRepository.findAll();
    }

    public Account getAccount(Integer id) {
        Account account = accountRepository.findById(id).isPresent() ? accountRepository.findById(id).get() : null;
        if (account == null) {
            throw new EntityNotFoundException("Account Not Found");
        }

        return account;
    }

    @Transactional
    public Account updateAccount(Integer id, String email, String firstName, String lastName, String password) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found"));

        account.update(email, password, firstName, lastName);

        return accountRepository.save(account);
    }

    public void deleteAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found"));

        accountRepository.delete(account);
    }
}
