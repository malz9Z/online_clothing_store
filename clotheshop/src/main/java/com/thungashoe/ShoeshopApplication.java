package com.thungashoe;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thungashoe.common.Role;
import com.thungashoe.domain.entity.User;
import com.thungashoe.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication

public class ShoeshopApplication {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	ApplicationRunner startup( ) {

	      return args -> {
	    	  if (userRepository.findByUsername("admin") == null){
	                 userRepository.save(
	                		User.builder()
	                		.username("admin")
	                		.password(passwordEncoder.encode("admin"))
	                		.roles(Arrays.asList(Role.USER.name(), Role.ADMIN.name()))
	                        .isDeleted(false)
	                        .build()
	                );
	                log.warn("admin user has been created with default password: admin, please change it");
	            }
	      };

	}
	
	public static void main(String[] args) {
		SpringApplication.run(ShoeshopApplication.class, args);
	}

}
