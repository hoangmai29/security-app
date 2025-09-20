package config;


import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepo,
                               UserRepository userRepo,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            // Tạo role nếu chưa có
            Role roleUser = roleRepo.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepo.save(new Role("ROLE_USER")));
            Role roleAdmin = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

            // Tạo admin nếu chưa có
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456")); // pass = 123456
                admin.setEnabled(true);
                admin.setRoles(Set.of(roleAdmin));
                userRepo.save(admin);
            }

            // Tạo user nếu chưa có
            if (userRepo.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("123456")); // pass = 123456
                user.setEnabled(true);
                user.setRoles(Set.of(roleUser));
                userRepo.save(user);
            }
        };
    }
}
