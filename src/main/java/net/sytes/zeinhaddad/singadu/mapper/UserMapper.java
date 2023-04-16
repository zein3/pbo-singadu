package net.sytes.zeinhaddad.singadu.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.sytes.zeinhaddad.singadu.dto.UserDto;
import net.sytes.zeinhaddad.singadu.entity.User;

public class UserMapper {
    public static User mapToUser(UserDto userDto) {
        User user = User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .email(userDto.getEmail())
            .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
            .role(userDto.getRole())
            .build();

        return user;
    }
}
