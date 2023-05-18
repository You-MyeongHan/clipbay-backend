package com.homepage.security.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homepage.security.user.entity.User;


public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findByUid(String uid);
}