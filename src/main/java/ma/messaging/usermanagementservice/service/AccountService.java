package ma.messaging.usermanagementservice.service;

import ma.messaging.usermanagementservice.payload.requests.LoginRequest;
import ma.messaging.usermanagementservice.payload.requests.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?> userRegister(RegisterRequest request);

    ResponseEntity<?> userLogin(LoginRequest request);

    ResponseEntity<?> userLogout();
}
