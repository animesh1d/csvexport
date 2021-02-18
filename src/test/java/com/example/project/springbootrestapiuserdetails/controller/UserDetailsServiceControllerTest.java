package com.example.project.springbootrestapiuserdetails.controller;

import com.example.project.springbootrestapiuserdetails.exception.ResourceNotFoundException;
import com.example.project.springbootrestapiuserdetails.model.Address;
import com.example.project.springbootrestapiuserdetails.model.User;
import com.example.project.springbootrestapiuserdetails.service.UserDetailsService;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceControllerTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserDetailsService userDetailsService;

    private static List<User> userList;

    @BeforeClass
    public static void init(){
        MockitoAnnotations.initMocks(UserDetailsServiceControllerTest.class);
        userStub();
    }

    @Test
    public void testCreateEmployee()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(userDetailsService.createUser(Mockito.any())).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userDetailsController.createUser(userList);

        Assert.assertEquals(responseEntity.getStatusCode().value(),201);
        Assert.assertEquals(responseEntity.getBody(),userList);
    }

    @Test
    public void testGetAllUsers()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(userDetailsService.getUsers()).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userDetailsController.getAllUsers();

        Assert.assertEquals(responseEntity.getStatusCode().value(),200);
        Assert.assertEquals(responseEntity.getBody(),userList);
    }

    @Test
    public void testUpdateUser() throws ResourceNotFoundException
    {
        String empId = "1";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(userDetailsService.updateUser(userList.get(0),"1")).thenReturn("SUCCESS");
        ResponseEntity<?> responseEntity = userDetailsController.updateUser(userList.get(0), empId);

        Assert.assertEquals(responseEntity.getStatusCode().value(),202);
        Assert.assertEquals(responseEntity.getBody(),"SUCCESS");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserException() throws ResourceNotFoundException
    {
        String empId = "1";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(userDetailsService.updateUser(userList.get(0),"1")).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<?> responseEntity = userDetailsController.updateUser(userList.get(0), empId);

        Assert.assertEquals(responseEntity.getStatusCode().value(),202);
        Assert.assertEquals(responseEntity.getBody(),"SUCCESS");
    }

    private static void userStub(){
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
        userList = new ArrayList<>();
        userList.add(user);
    }
}
