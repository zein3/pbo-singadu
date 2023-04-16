package net.sytes.zeinhaddad.singadu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> index() {
        return this.userService.getUsers();
    }
}
