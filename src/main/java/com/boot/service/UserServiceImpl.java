package com.boot.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.Model.User;
import com.boot.repo.UserRepository;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {


	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	private UserRepository repo;
	

	@Override
	public Integer saveUser(User user) {
		
		//encode the password here
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		
		return repo.save(user).getId();
	}

	
	//get user by username
	@Override
	public Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return repo.findByUsername(username);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username){
		
		Optional<User> opt= findByUsername(username);
		if(opt.isEmpty())
			throw new UsernameNotFoundException("user not exist");
		
		//Read User from database
		User user = opt.get();
		return new org.springframework.security.core.userdetails.User(username ,user.getPassword(),user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}
	
	

}
