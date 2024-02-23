package ma.messaging.usermanagementservice.controller;

import lombok.extern.log4j.Log4j2;
import ma.messaging.usermanagementservice.auth.AuthenticationResponse;
import ma.messaging.usermanagementservice.config.JwtUtil;
import ma.messaging.usermanagementservice.auth.AuthenticationService;
import ma.messaging.usermanagementservice.auth.login.LoginRequest;
import ma.messaging.usermanagementservice.auth.login.LoginResponse;
import ma.messaging.usermanagementservice.auth.register.RegisterRequest;
import ma.messaging.usermanagementservice.auth.register.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;


    @Autowired
    public AuthController(AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> userRegister(@RequestBody RegisterRequest request) {
        RegisterResponse response = authenticationService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest request) {
        LoginResponse response = authenticationService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getTest(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        AuthenticationResponse authResponse = authenticationService.refreshToken(authHeader);
        if (authResponse.isTokenValid() ||  authResponse.getRefreshToken() != null) {
            return new ResponseEntity<>("Authorized user: " + authResponse.getRefreshToken(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
