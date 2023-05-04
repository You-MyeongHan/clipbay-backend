package com.homepage.user.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public Optional<User> findById(Integer id);
    public Optional<User> findByEmail(String email);
}