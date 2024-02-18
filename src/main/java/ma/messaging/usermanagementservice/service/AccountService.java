package ma.messaging.usermanagementservice.service;

import ma.messaging.usermanagementservice.user.register.RegisterRequest;

public interface AccountService {
    boolean register(RegisterRequest request);
}
