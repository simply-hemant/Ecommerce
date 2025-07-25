package com.simply.service;

import com.simply.exception.UserException;
import com.simply.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;


}
