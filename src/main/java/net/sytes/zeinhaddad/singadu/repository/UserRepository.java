package net.sytes.zeinhaddad.singadu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sytes.zeinhaddad.singadu.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
