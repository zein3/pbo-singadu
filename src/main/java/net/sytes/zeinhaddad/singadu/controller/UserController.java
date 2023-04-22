package net.sytes.zeinhaddad.singadu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.form.ChangePasswordForm;
import net.sytes.zeinhaddad.singadu.form.ChangeProfileForm;
import net.sytes.zeinhaddad.singadu.form.CreateAccountForm;
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

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    @GetMapping("/role/{role}")
    public List<User> userWithRole(@PathVariable String role) {
        return this.userService.getUsersWithRole(role);
    }

    @GetMapping("/supervised/{id}")
    public List<User> supervisedBy(@PathVariable Long id) {
        return this.userService.getUsersSupervisedBy(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long store(@Valid @RequestBody CreateAccountForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(encoder.encode(form.getPassword()));
        user.setRole(form.getRole());

        return userService.saveUser(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long update(@Valid @RequestBody User user) {
        if (!user.getRole().equals("PENCACAH")) {
            user.setSupervisor(null);
        }
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long destroy(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
        return 1l;
    }

    @PutMapping("/profile")
    public Long updateProfile(@Valid @RequestBody ChangeProfileForm form, Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        return userService.updateUser(user);
    }

    @PutMapping("/password")
    public String updatePassword(@Valid @RequestBody ChangePasswordForm form, Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());

        // check old password
        if (!encoder.matches(form.getOldPassword(), user.getPassword())) {
            // user password does not match
            return "Wrong password";
        }

        // new password == confirm password
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            return "Passwords do not match";
        }

        user.setPassword(encoder.encode(form.getNewPassword()));
        userService.updateUser(user);

        return "success";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException err) {
        Map<String, String> errors = new HashMap<>();
        err.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
