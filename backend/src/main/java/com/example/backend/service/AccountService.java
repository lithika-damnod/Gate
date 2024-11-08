package com.example.backend.service;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.DTO.AccountDTOMapper;
import com.example.backend.models.Account;
import com.example.backend.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    // repositories
    private final AccountRepository accountRepository;
    private final AccountDTOMapper accountDTOMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountDTOMapper accountDTOMapper) {
        this.accountRepository = accountRepository;
        this.accountDTOMapper = accountDTOMapper;
    }

    public List<AccountDTO> getAccount() {
        return accountRepository.findAll()
                .stream()
                .map(accountDTOMapper).collect(Collectors.toList());
    }

    public AccountDTO getAccount(Integer id) {
        Account account = accountRepository.findById(id).isPresent() ? accountRepository.findById(id).get() : null;
        if (account == null) {
            throw new EntityNotFoundException("Account Not Found");
        }

        return accountDTOMapper.apply(account);
    }

    @Transactional
    public AccountDTO updateAccount(Integer id, String email, String firstName, String lastName, String password) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found"));

        account.update(email, password, firstName, lastName);

        return accountDTOMapper.apply(accountRepository.save(account));
    }

    public void deleteAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found"));

        accountRepository.delete(account);
    }
}
