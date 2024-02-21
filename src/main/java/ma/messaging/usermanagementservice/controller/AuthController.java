package ma.messaging.usermanagementservice.controller;

import ma.messaging.usermanagementservice.service.AccountService;
import ma.messaging.usermanagementservice.user.login.LoginRequest;
import ma.messaging.usermanagementservice.user.login.LoginResponse;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;
import ma.messaging.usermanagementservice.user.register.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;

    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody RegisterRequest request) {
        RegisterResponse response = accountService.register(request);

        if (response.equals(RegisterResponse.USERNAME_EXISTS))
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        else if (response.equals(RegisterResponse.EMAIL_EXISTS))
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Successful user registration", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginRequest request) {
        LoginResponse response = accountService.login(request);

        if (response.equals(LoginResponse.NON_EXISTENT_ACCOUNT) || response.equals(LoginResponse.WRONG_CREDENTIALS))
            return new ResponseEntity<>("Failed login: incorrect username and/or password", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("Successfully logged in", HttpStatus.OK);
    }
}
