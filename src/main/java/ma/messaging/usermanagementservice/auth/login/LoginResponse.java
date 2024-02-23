package ma.messaging.usermanagementservice.auth.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    @JsonProperty("response_msg")
    private LoginResponseMessage responseMsg;

    @JsonProperty("token")
    private String token;
}

