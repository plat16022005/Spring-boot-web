package plat.dev.security;

import plat.dev.user.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository repo;
  public UserDetailsServiceImpl(UserRepository repo){ this.repo = repo; }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var u = repo.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    var authorities = org.springframework.security.core.authority.AuthorityUtils.createAuthorityList("ROLE_" + u.getRole());
    return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), u.isEnabled(),
        true, true, true, authorities);
  }
}
