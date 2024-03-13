package ma.messaging.authservice.repository;

import ma.messaging.authservice.model.ERole;
import ma.messaging.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from  Role r where r.name = ?1")
    Optional<Role> findByName(ERole name);
}
