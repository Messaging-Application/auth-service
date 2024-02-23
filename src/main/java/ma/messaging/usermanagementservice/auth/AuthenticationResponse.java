package ma.messaging.usermanagementservice.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("valid")
    private boolean isTokenValid;

    @JsonIgnoreProperties(value = "refresh_token", ignoreUnknown = true)
    private String refreshToken;
}
