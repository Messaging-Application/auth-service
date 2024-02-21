package ma.messaging.usermanagementservice.controller;

import ma.messaging.usermanagementservice.service.AccountService;
import ma.messaging.usermanagementservice.user.login.LoginRequest;
import ma.messaging.usermanagementservice.user.login.LoginResponse;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;

    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody RegisterRequest request) {
        if (accountService.register(request))
            return new ResponseEntity<>("success", HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginRequest request) {
        LoginResponse response = accountService.login(request);

        if (response.equals(LoginResponse.NON_EXISTENT_ACCOUNT) || response.equals(LoginResponse.WRONG_CREDENTIALS))
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
