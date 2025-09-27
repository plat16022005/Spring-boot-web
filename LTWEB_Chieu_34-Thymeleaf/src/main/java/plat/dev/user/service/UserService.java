package plat.dev.user.service;

import plat.dev.user.entity.User;
import org.springframework.data.domain.*;

import java.util.Optional;

public interface UserService {
	Page<User> search(String q, Pageable pageable);

	Page<User> findAll(Pageable pageable);

	Optional<User> findById(Long id);

	User save(User u);

	void deleteById(Long id);
}
