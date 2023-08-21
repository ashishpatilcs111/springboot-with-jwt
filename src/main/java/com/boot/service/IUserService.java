package com.boot.service;

import java.util.Optional;

import com.boot.Model.User;

public interface IUserService {
	
	Integer saveUser(User user);
	
	Optional<User> findByUsername(String username);

}
