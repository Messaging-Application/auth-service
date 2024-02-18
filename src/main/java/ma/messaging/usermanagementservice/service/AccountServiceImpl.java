package ma.messaging.usermanagementservice.service;

import ma.messaging.usermanagementservice.model.Account;
import ma.messaging.usermanagementservice.repository.AccountRepository;
import ma.messaging.usermanagementservice.user.login.LoginRequest;
import ma.messaging.usermanagementservice.user.login.LoginResponse;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public boolean register(RegisterRequest request) {
        if (accountRepository.existsById(request.getUsername())) return false;

        Account account = new Account(
                request.getUsername(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail());

        accountRepository.save(account);

        return true;
    }

    public LoginResponse login(LoginRequest request) {
        Optional<Account> user = accountRepository.findById(request.getUsername());

        if (user.isEmpty()) return LoginResponse.NON_EXISTENT_ACCOUNT;
        else if (!user.get().getPassword().equals(request.getPassword()))
            return LoginResponse.WRONG_CREDENTIALS;

        return LoginResponse.LOGGED_IN;
    }
}
