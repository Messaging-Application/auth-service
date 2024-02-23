package ma.messaging.usermanagementservice.auth.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public enum RegisterResponse {
    REGISTERED,
    USERNAME_EXISTS,
    EMAIL_EXISTS;
}

