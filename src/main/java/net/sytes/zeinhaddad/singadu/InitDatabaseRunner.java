package net.sytes.zeinhaddad.singadu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.repository.UserRepository;

@Component
public class InitDatabaseRunner implements CommandLineRunner {
    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setName("Root Account");
            user.setEmail(env.getProperty("spring.security.user.email"));
            user.setPassword(encoder.encode(env.getProperty("spring.security.user.password")));
            user.setRole("ADMIN");

            userRepository.save(user);
        }
    }
}
