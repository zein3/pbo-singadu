package net.sytes.zeinhaddad.singadu.service;

import java.util.List;

import net.sytes.zeinhaddad.singadu.entity.User;

public interface IUserService {
    public List<User> getUsers();
    public User getUser(Long id);
    public void updateUser(User user);
    public void deleteUser(Long id);
    public void saveUser(Long id);
}
