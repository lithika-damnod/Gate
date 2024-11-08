package com.example.backend.DTO;

import com.example.backend.models.Account;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class AccountDTOMapper implements Function<Account, AccountDTO> {
    @Override
    public AccountDTO apply(Account account) {
        return new AccountDTO(
            account.getId(),
            account.getEmail(),
            account.getFirstName(),
            account.getLastName()
        );
    }
}
