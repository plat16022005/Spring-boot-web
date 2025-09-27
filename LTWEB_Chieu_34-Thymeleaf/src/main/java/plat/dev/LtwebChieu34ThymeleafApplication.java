package plat.dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import plat.dev.user.entity.User;
import plat.dev.user.repository.UserRepository;

@SpringBootApplication
public class LtwebChieu34ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(LtwebChieu34ThymeleafApplication.class, args);
    }

    // Seed admin account
    @Bean
    CommandLineRunner seedAdmin(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User u = new User(null, null, null, null, false, null, null, null);
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin123"));
                u.setRole("ADMIN");
                u.setEnabled(true);
                repo.save(u);
            }
        };
    }
}
