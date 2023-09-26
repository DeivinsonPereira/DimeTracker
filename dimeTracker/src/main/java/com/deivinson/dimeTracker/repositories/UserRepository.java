package com.deivinson.dimeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.dimeTracker.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
