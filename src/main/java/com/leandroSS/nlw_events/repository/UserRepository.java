package com.leandroSS.nlw_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandroSS.nlw_events.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
