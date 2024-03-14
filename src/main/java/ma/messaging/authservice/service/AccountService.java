package ma.messaging.authservice.service;

import ma.messaging.authservice.payload.requests.LoginRequest;
import ma.messaging.authservice.payload.requests.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?> userRegister(RegisterRequest request);

    ResponseEntity<?> userLogin(LoginRequest request);

    ResponseEntity<?> userLogout();

    ResponseEntity<?> validateToken(String token);
}
