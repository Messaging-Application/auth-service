package ma.messaging.usermanagementservice.service;

import ma.messaging.usermanagementservice.model.Account;
import ma.messaging.usermanagementservice.repository.AccountRepository;
import ma.messaging.usermanagementservice.user.login.LoginRequest;
import ma.messaging.usermanagementservice.user.login.LoginResponse;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;
import ma.messaging.usermanagementservice.user.register.RegisterResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public RegisterResponse register(RegisterRequest request) {
        if (accountRepository.existsByUsername(request.getUsername())) return RegisterResponse.USERNAME_EXISTS;
        if (accountRepository.existsByEmail(request.getEmail())) return RegisterResponse.EMAIL_EXISTS;

        Account account = new Account(
                request.getUsername(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail());

        accountRepository.save(account);

        return RegisterResponse.REGISTERED;
    }

    public LoginResponse login(LoginRequest request) {
        Optional<Account> user = accountRepository.findByUsername(request.getUsername());

        if (user.isEmpty()) return LoginResponse.NON_EXISTENT_ACCOUNT;
        else if (!user.get().getPassword().equals(request.getPassword()))
            return LoginResponse.WRONG_CREDENTIALS;

        return LoginResponse.LOGGED_IN;
    }
}
