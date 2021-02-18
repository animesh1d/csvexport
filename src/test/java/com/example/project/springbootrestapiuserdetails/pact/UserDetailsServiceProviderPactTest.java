package com.example.project.springbootrestapiuserdetails.pact;


import au.com.dius.pact.provider.junit.*;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.example.project.springbootrestapiuserdetails.SpringBootRestApiUserDetailsApplication;
import com.example.project.springbootrestapiuserdetails.controller.UserDetailsController;
import com.example.project.springbootrestapiuserdetails.model.Address;
import com.example.project.springbootrestapiuserdetails.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootRestApiUserDetailsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8089")
@Provider("user_details_provider")
@PactFolder("src/test/resource/pacts")
public class UserDetailsServiceProviderPactTest {

    private String providerUrl = "http://localhost:8089/";

    @TestTarget
    public final Target target = new HttpTarget("localhost", 8089);

    @MockBean
    private UserDetailsController userDetailsController;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8089, ""));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State({"get user details"})
    public void getUsersState() {
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
        when(userDetailsController.getAllUsers()).thenReturn(new ResponseEntity<>(userList,HttpStatus.OK));
    }

}
