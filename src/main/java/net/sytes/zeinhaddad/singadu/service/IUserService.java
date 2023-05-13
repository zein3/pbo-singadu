package net.sytes.zeinhaddad.singadu.service;

import java.util.List;

import net.sytes.zeinhaddad.singadu.dto.UserDto;

public interface IUserService {
    public List<UserDto> getUsers();
    public List<UserDto> getUsersWithRole(String role);
    public List<UserDto> getUsersSupervisedBy(Long id);
    public UserDto getUserByEmail(String email);
    public UserDto getUser(Long id);
    public Long updateUser(UserDto user);
    public void deleteUser(Long id);
    public Long saveUser(UserDto id);
}
