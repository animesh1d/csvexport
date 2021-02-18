package com.example.project.springbootrestapiuserdetails.service;

import com.example.project.springbootrestapiuserdetails.exception.ResourceNotFoundException;
import com.example.project.springbootrestapiuserdetails.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDetailsService {

    public User getUser(Long empId) throws ResourceNotFoundException;
    public String updateUser(User user, String empId) throws ResourceNotFoundException;
    public List<User> getUsers();
    public List<User> createUser(List<User> users);
}
