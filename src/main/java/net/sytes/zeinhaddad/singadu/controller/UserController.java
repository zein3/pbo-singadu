package net.sytes.zeinhaddad.singadu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotEmpty;
import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public List<User> index() {
        return this.userService.getUsers();
    }

    @PostMapping
    public Long store(@NotEmpty @RequestParam("name") String name,
                      @NotEmpty @RequestParam("email") String email,
                      @NotEmpty @RequestParam("password") String password,
                      @NotEmpty @RequestParam("role") String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);

        return this.userService.saveUser(user);
    }
}
