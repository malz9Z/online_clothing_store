package com.thungashoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thungashoe.domain.entity.User;
import com.thungashoe.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<User> getAllUsers(int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return userRepository.findAll(pageable);
	}

	public Page<User> searchUsers(String searchTerm, int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);

		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return userRepository.findByUsernameContainingOrEmailContainingOrPhoneContaining(searchTerm, searchTerm,
				searchTerm, pageable);
	}

	public Page<User> getUsersByRole(String role, int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return userRepository.findByRolesContaining(role, pageable);
	}
	
	public User getUserById(String userId) {
		return userRepository.findById(userId).orElse(null);
    }
	
	public void addUser(User user) {
		user.setIsDeleted(false);
        userRepository.save(user);
    }
	
	public void updateUser(String userId, User updatedUser) {
        User existingUser = getUserById(userId);
        if (existingUser != null) {
        	updatedUser.setId(userId);
        	userRepository.save(updatedUser);
        }
    }
	
	public void statusUser(String userId) {
        User user = getUserById(userId);
        if (user != null) {
        	try {
                userRepository.delete(user);
        	}catch (DataIntegrityViolationException e) {
        		user.setIsDeleted(!user.getIsDeleted());
                userRepository.save(user);
			}
        }
    }
}
