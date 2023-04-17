package net.sytes.zeinhaddad.singadu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAuthority('ADMIN')")
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
                      @NotEmpty @Email @RequestParam("email") String email,
                      @NotEmpty @RequestParam("password") String password,
                      @NotEmpty @RequestParam("role") String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);

        return this.userService.saveUser(user);
    }

    @PutMapping
    public Long update(@RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public Long destroy(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
        return 1l;
    }
}
