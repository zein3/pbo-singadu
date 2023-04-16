package net.sytes.zeinhaddad.singadu.service;

import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUser(Long id) {
		// TODO Auto-generated method stub
		
	}

}
