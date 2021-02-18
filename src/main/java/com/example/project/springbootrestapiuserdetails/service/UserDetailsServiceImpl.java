package com.example.project.springbootrestapiuserdetails.service;

import com.example.project.springbootrestapiuserdetails.exception.ResourceNotFoundException;
import com.example.project.springbootrestapiuserdetails.model.Address;
import com.example.project.springbootrestapiuserdetails.model.User;
import com.example.project.springbootrestapiuserdetails.repository.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public List<User> createUser(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Transactional(rollbackOn = { Exception.class })
    @Override
    @HystrixCommand(fallbackMethod = "getDefaultUsers_Fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
            })
    public List<User> getUsers()
    {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    @SuppressWarnings("unused")
    private List<User> getDefaultUsers_Fallback() {
        User user = new User();
        Address address = new Address();
        address.setCity("Sydney");
        address.setState("NSW");
        address.setStreet("Jessie ST");
        address.setPostcode("2145");
        user.setFirstName("Animesh");
        user.setLastName("Dutta");
        user.setGender("M");
        user.setEmpId(1);
        user.setAddress(address);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        return userList;
    }

    @Transactional(rollbackOn = { ResourceNotFoundException.class, Exception.class})
    @Override
    public User getUser(Long empId) throws ResourceNotFoundException{
        return userRepository.findById(empId).orElseThrow(() -> new ResourceNotFoundException("User not found for Employee ID :: " + empId));
    }

    @Transactional(rollbackOn = { ResourceNotFoundException.class, Exception.class})
    @Override
    @HystrixCommand(fallbackMethod = "updateDefaultUser_Fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
            })
    public String updateUser(final User user, final String empId) throws ResourceNotFoundException
    {
        User existingUser = getUser(Long.valueOf(empId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmpId(user.getEmpId());
        existingUser.setGender(user.getGender());
        existingUser.getAddress().setCity(user.getAddress().getCity());
        existingUser.getAddress().setStreet(user.getAddress().getStreet());
        existingUser.getAddress().setState(user.getAddress().getState());
        existingUser.getAddress().setPostcode(user.getAddress().getPostcode());
        userRepository.save(existingUser);
        return "USER DETAILS UPDATED";
    }

    @SuppressWarnings("unused")
    public String updateDefaultUser_Fallback(final User user, final String empId, final Throwable cause) throws ResourceNotFoundException
    {
        if (cause != null && cause instanceof HystrixTimeoutException) {
            return "No Response From database at this moment. " +
                    " Service will be back shortly - " + new Date();
        }
        throw new ResourceNotFoundException("User not found for Employee ID :: " + empId);
    }
}
