package ma.messaging.usermanagementservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ma.messaging.usermanagementservice.model.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

// @Data includes @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Data
@NoArgsConstructor
public class AccountDTO implements UserDetails {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime registrationTime;

    public AccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
