package ma.messaging.usermanagementservice.repository;

import ma.messaging.usermanagementservice.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
public class AccountRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    }

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testExistsByEmail() {
        // Setup data scenario
        Account account = new Account();
        account.setEmail("test@example.com");
        accountRepository.save(account);

        // Test existsByEmail
        assertTrue(accountRepository.existsByEmail(account.getEmail()));
        assertFalse(accountRepository.existsByEmail("notfound@example.com"));
    }

    @Test
    void testExistsByUsername() {
        // Setup data scenario
        Account account = new Account();
        account.setUsername("user");
        accountRepository.save(account);

        // Test existsByUsername
        assertTrue(accountRepository.existsByUsername(account.getUsername()));
        assertFalse(accountRepository.existsByUsername("non-user"));
    }

    @Test
    void testFindByUsername() {
        // Setup data scenario
        Account account = new Account();
        account.setUsername("testUser");
        account.setEmail("test@example.com");
        // Ensure ID or other required fields are set up if necessary
        accountRepository.save(account);

        // Test findByUsername
        Optional<Account> foundAccount = accountRepository.findByUsername("testUser");
        assertTrue(foundAccount.isPresent(), "Account should be found with username testUser");
        assertEquals("testUser", foundAccount.get().getUsername(), "The username should match");

        // Test findByUsername for a username that does not exist
        Optional<Account> notFoundAccount = accountRepository.findByUsername("nonExistingUser");
        assertFalse(notFoundAccount.isPresent(), "Account should not be found");
    }
}
