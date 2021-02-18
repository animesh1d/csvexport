package com.example.project.springbootrestapiuserdetails.controller;

import com.example.project.springbootrestapiuserdetails.exception.ResourceNotFoundException;
import com.example.project.springbootrestapiuserdetails.model.User;
import com.example.project.springbootrestapiuserdetails.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;

/**
 * The type User controller.
 *
 * @author Givantha Kalansuriya
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/users")
    public ResponseEntity<List<User>> createUser(@RequestBody List<User> users) {
        return new ResponseEntity<>(userDetailsService.createUser(users), HttpStatus.CREATED);
    }

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userDetailsService.getUsers(), HttpStatus.OK);
    }

    /**
     * Update user response entity.
     *
     * @param user the user details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody final User user,
                    @PathVariable(value = "id") @NotBlank(message = "Employee Id cannot be blank") @Pattern(message = "Employee Id must be a number",regexp="^(0|[1-9][0-9]*)$") String empId)
            throws ResourceNotFoundException {

        //@PathVariable(value = "id") @NotBlank(message = "Employee Id cannot be blank") @Pattern(message = "Employee Id must be a number",regexp="^(0|[1-9][0-9]*)$") String empId,

        String result = userDetailsService.updateUser(user, empId);
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }

   /* @PutMapping("/put-request")
    public void doResetPassword(@RequestBody String password) {
        System.out.println("PUT MAPPING");

    }*/
}
