package com.example.project.springbootrestapiuserdetails.repository;

import com.example.project.springbootrestapiuserdetails.exception.ResourceNotFoundException;
import com.example.project.springbootrestapiuserdetails.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
}
