package ma.messaging.usermanagementservice.repository;

import ma.messaging.usermanagementservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByUsername(String username);
    Account findByUsername(String username);
    boolean existsByEmail(String email);
}
