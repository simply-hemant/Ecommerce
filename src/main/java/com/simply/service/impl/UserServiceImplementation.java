package com.simply.service.impl;


import com.simply.exception.UserException;
import com.simply.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simply.config.JwtProvider;
import com.simply.model.User;
import com.simply.repository.PasswordResetTokenRepository;
import com.simply.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {


	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		
		User user = userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		return user;
	}


	@Override
	public User findUserByEmail(String username) throws UserException {
		
		User user=userRepository.findByEmail(username);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new UserException("user not exist with username "+username);
	}



}
