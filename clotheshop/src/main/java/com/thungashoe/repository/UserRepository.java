package com.thungashoe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
		
	User findByUsername(String username);
	
	Page<User> findByUsernameContainingOrEmailContainingOrPhoneContaining(
            String username, String email, String phone, Pageable pageable);
	
	Page<User> findByRolesContaining(String role, Pageable pageable);
}