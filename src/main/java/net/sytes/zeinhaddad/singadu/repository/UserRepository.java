package net.sytes.zeinhaddad.singadu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sytes.zeinhaddad.singadu.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(String role);
    List<User> findBySupervisorId(Long id);
}
