package ma.messaging.usermanagementservice.service;

import ma.messaging.usermanagementservice.user.login.LoginRequest;
import ma.messaging.usermanagementservice.user.login.LoginResponse;
import ma.messaging.usermanagementservice.user.register.RegisterRequest;

public interface AccountService {
    boolean register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
