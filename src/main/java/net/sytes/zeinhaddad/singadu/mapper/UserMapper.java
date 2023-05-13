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
            .password(userDto.getPassword())
            .role(userDto.getRole())
            .build();

        return user;
    }

    public static UserDto mapToDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .supervisor(UserMapper.mapToDto(user.getSupervisor()))
            .build();
        
        return dto;
    }
}
