package net.sytes.zeinhaddad.singadu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

	@Override
	public List<User> getUsers() {
        List<User> users = this.userRepository.findAll();
        return users;
	}

	@Override
	public User getUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return (user.isPresent()) ? user.get() : null;
	}

	@Override
	public Long updateUser(User user) {
		this.userRepository.save(user);
        return user.getId();
	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public Long saveUser(User user) {
        this.userRepository.save(user);
        return user.getId();
	}

}
