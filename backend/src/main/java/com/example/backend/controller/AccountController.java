package com.example.backend.controller;

import com.example.backend.dto.AccountDTO;
import com.example.backend.models.Account;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/account")
public class AccountController {

    // services
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping
    public List<AccountDTO> getAccount(@RequestParam(required = false) String email) {
        return accountService.getAccount(email);
    }

    @GetMapping("{id}")
    public AccountDTO getAccount(@PathVariable("id") Integer id) {
        return accountService.getAccount(id);
    }

    @PutMapping("{id}")
    public AccountDTO putAccount(@PathVariable("id") Integer id, @RequestBody Account account) {
        return accountService.updateAccount(id, account.getEmail(), account.getFirstName(), account.getLastName(), account.getPassword());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build(); // 204: No Content Status
    }

    // @PostMapping for this component will be handled by ('/auth')
}
