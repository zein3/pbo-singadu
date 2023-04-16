package net.sytes.zeinhaddad.singadu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.repository.UserRepository;

@SpringBootApplication
public class SingaduApplication {

    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(SingaduApplication.class, args);
	}

    @Bean
    CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setName("Root Account");
                user.setEmail(env.getProperty("spring.security.user.email"));
                user.setPassword(encoder.encode(env.getProperty("spring.security.user.password")));
                user.setRole("ADMIN");

                userRepository.save(user);
            }
        };
    }

}
