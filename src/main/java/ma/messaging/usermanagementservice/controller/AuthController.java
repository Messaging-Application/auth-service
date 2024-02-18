package ma.messaging.usermanagementservice.controller;

import ma.messaging.usermanagementservice.service.AccountService;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;
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
        if (accountService.register(request))
            return new ResponseEntity<>("success", HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
