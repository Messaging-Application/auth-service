package ma.messaging.usermanagementservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@ToString
@Table(name = "account")
public class Account {

    static final int MIN_USERNAME_SIZE = 4;
    static final int MAX_USERNAME_SIZE = 30;
    static final int MIN_PASSWORD_SIZE = 8;
    static final int MAX_PASSWORD_SIZE = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = MIN_USERNAME_SIZE, max = MAX_USERNAME_SIZE)
    private String username;

    @Size(min = MIN_PASSWORD_SIZE, max = MAX_PASSWORD_SIZE)
    private String password;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String email;
    private LocalDateTime registrationTime;

    public Account(String username, String password, String firstName, String lastName, String email) {
        this();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Account() {
        registrationTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
