package com.example.nalmuri.repository;

import com.example.nalmuri.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserid(String userid);

    Optional<User> findByEmail(String email);

    boolean existsByUserid(String userid);

    List<User> findAll();



    void delete(User user);
}