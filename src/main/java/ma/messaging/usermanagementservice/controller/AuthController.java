package ma.messaging.usermanagementservice.controller;

import jakarta.validation.Valid;
import ma.messaging.usermanagementservice.payload.requests.LoginRequest;
import ma.messaging.usermanagementservice.payload.requests.RegisterRequest;
import ma.messaging.usermanagementservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@Valid @RequestBody RegisterRequest request) {
        return accountService.userRegister(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginRequest request) {
        return accountService.userLogin(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> userLogout() {
        return accountService.userLogout();
    }
}
