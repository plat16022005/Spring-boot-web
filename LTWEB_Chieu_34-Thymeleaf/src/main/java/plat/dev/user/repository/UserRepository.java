package plat.dev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import plat.dev.user.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Page<User> findByUsernameContainingIgnoreCase(String q, Pageable pageable);
}
