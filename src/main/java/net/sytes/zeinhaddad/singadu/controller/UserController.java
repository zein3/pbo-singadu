package net.sytes.zeinhaddad.singadu.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import net.sytes.zeinhaddad.singadu.dto.UserDto;
import net.sytes.zeinhaddad.singadu.form.ChangePasswordForm;
import net.sytes.zeinhaddad.singadu.form.ChangeProfileForm;
import net.sytes.zeinhaddad.singadu.form.CreateAccountForm;
import net.sytes.zeinhaddad.singadu.mapper.UserMapper;
import net.sytes.zeinhaddad.singadu.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder encoder;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public List<UserDto> index() {
        return this.userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    @GetMapping("/role/{role}")
    public List<UserDto> userWithRole(@PathVariable String role) {
        return this.userService.getUsersWithRole(role);
    }

    @GetMapping("/supervised/{id}")
    public List<UserDto> supervisedBy(@PathVariable Long id) {
        return this.userService.getUsersSupervisedBy(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long store(@Valid @RequestBody CreateAccountForm form) {
        UserDto userDto = UserDto.builder()
            .name(form.getName())
            .email(form.getEmail())
            .password(encoder.encode(form.getPassword()))
            .role(form.getRole())
            .build();

        System.out.println(encoder.encode(form.getPassword()));
        System.out.println(userDto.getPassword());
        System.out.println(UserMapper.mapToUser(userDto).getPassword());

        return userService.saveUser(userDto);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long update(@Valid @RequestBody UserDto user, @PathVariable Long userId) {
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
        UserDto user = userService.getUserByEmail(auth.getName());
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        return userService.updateUser(user);
    }

    @PutMapping("/password")
    public Long updatePassword(@Valid @RequestBody ChangePasswordForm form, Authentication auth) {
        UserDto user = userService.getUserByEmail(auth.getName());

        // check old password
        if (!encoder.matches(form.getOldPassword(), user.getPassword())) {
            // user password does not match
            return 0l;
        }

        // new password == confirm password
        // if (!form.getNewPassword().equals(form.getConfirmPassword())) {
        //     return "Passwords do not match";
        // }

        user.setPassword(encoder.encode(form.getNewPassword()));
        userService.updateUser(user);

        return 1l;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleValidationException(SQLIntegrityConstraintViolationException err) {
        Map<String, String> errors = new HashMap<>();
        int errorCode = err.getErrorCode();

        switch(errorCode) {
            case 1062:
                errors.put("Error", "Email sudah terdaftar");
                break;
            default:
                errors.put("Error code", String.valueOf(errorCode));
                break;
        }

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Map<String, String> handleValidationException(NullPointerException err) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "The server encountered an error");
        logger.warn(err.getMessage());

        return errors;
    }
}
