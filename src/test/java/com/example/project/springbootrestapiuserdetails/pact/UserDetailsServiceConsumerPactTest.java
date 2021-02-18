package com.example.project.springbootrestapiuserdetails.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "user_details_provider", port = "8089")
public class UserDetailsServiceConsumerPactTest {

    @Pact(provider="user_details_provider", consumer="user_details_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        String json = "[\n" +
                "    {\n" +
                "        \"firstName\": \"Animesh\",\n" +
                "        \"lastName\": \"Dutta\",\n" +
                "        \"empId\": 1,\n" +
                "        \"gender\": \"M\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Jessie ST\",\n" +
                "            \"city\": \"Sydney\",\n" +
                "            \"state\": \"NSW\",\n" +
                "            \"postcode\": \"2145\"\n" +
                "        }\n" +
                "    }]";

        return builder
                .given("get user details")
                .uponReceiving("User Details Test")
                .path("/api/v1/users")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(json)
                .toPact();
    }

    @Test
    void test(MockServer mockServer) throws Exception {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/api/v1/users").execute().returnResponse();
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(),200);
    }
}
