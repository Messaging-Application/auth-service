package ma.messaging.usermanagementservice.auth;

import ma.messaging.usermanagementservice.auth.login.LoginRequest;
import ma.messaging.usermanagementservice.auth.login.LoginResponse;
import ma.messaging.usermanagementservice.auth.register.RegisterRequest;
import ma.messaging.usermanagementservice.auth.register.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    AuthenticationResponse refreshToken(String authHeader);
}
