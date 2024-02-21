package ma.messaging.usermanagementservice.service;

import ma.messaging.usermanagementservice.user.login.LoginRequest;
import ma.messaging.usermanagementservice.user.login.LoginResponse;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;
import ma.messaging.usermanagementservice.user.register.RegisterResponse;

public interface AccountService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
