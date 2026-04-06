package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo  extends JpaRepository<User, String> {
    public User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);

}
