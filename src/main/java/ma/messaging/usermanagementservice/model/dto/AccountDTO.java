package ma.messaging.usermanagementservice.model.dto;

import lombok.Data;
import ma.messaging.usermanagementservice.model.Account;

import java.time.LocalDateTime;

// @Data includes @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Data
public class AccountDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime registrationTime;

    public static AccountDTO fromEntity(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setUsername(account.getUsername());
        dto.setPassword(account.getPassword());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setRegistrationTime(account.getRegistrationTime());
        return dto;
    }
}
