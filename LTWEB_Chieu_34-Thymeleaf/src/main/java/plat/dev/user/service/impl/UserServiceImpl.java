package plat.dev.user.service.impl;

import plat.dev.user.entity.User;
import plat.dev.user.repository.UserRepository;
import plat.dev.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repo;

	public Page<User> search(String q, Pageable pageable) {
		return (q == null || q.isBlank()) ? repo.findAll(pageable)
				: repo.findByUsernameContainingIgnoreCase(q.trim(), pageable);
	}

	public Page<User> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public Optional<User> findById(Long id) {
		return repo.findById(id);
	}

	public User save(User u) {
		return repo.save(u);
	}

	public void deleteById(Long id) {
		repo.deleteById(id);
	}
}
