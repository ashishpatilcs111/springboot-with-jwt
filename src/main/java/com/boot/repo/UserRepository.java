package com.boot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.Model.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
}
