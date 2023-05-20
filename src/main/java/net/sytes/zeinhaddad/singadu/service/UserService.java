package net.sytes.zeinhaddad.singadu.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sytes.zeinhaddad.singadu.dto.UserDto;
import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.mapper.UserMapper;
import net.sytes.zeinhaddad.singadu.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

	@Override
	public List<UserDto> getUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
            .map(user -> UserMapper.mapToDto(user))
            .collect(Collectors.toList());
	}

    @Override
    public List<UserDto> getUsersWithRole(String role) {
        List<User> users = this.userRepository.findByRole(role);
        return users.stream()
            .map(user -> UserMapper.mapToDto(user))
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersSupervisedBy(Long id) {
        List<User> users = this.userRepository.findBySupervisorId(id);
        return users.stream()
            .map(user -> UserMapper.mapToDto(user))
            .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserMapper.mapToDto(user);
    }

	@Override
	public UserDto getUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return (user.isPresent()) ? UserMapper.mapToDto(user.get()) : null;
	}

	@Override
	public Long updateUser(UserDto user) {
		this.userRepository.save(UserMapper.mapToUser(user));
        return user.getId();
	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public Long saveUser(UserDto user) {
        this.userRepository.save(UserMapper.mapToUser(user));
        return user.getId();
	}

    @Override
    public long getPencacahCount() {
        return this.userRepository.countByRole("PENCACAH");
    }

    @Override
    public long getPengawasCount() {
        return this.userRepository.countByRole("PENGAWAS");
    }

}
