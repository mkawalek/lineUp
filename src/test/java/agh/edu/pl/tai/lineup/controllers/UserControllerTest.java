package agh.edu.pl.tai.lineup.controllers;

import agh.edu.pl.tai.lineup.api.requests.user.UserAuthenticationRequest;
import agh.edu.pl.tai.lineup.api.requests.user.UserRegistrationRequest;
import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.valueobject.Technology;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

import static agh.edu.pl.tai.lineup.TestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class UserControllerTest {

    private static final int embeddedMongoMinPort = 30000;
    private static final int embeddedMongoMaxPort = 30100;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeTest
    public void setUpEmbeddedMongo() throws IOException {
        arrangeEmbeddedMongo(embeddedMongoMinPort, embeddedMongoMaxPort);
    }

    @AfterTest
    public void ceaseEmbeddedMongo() {
        stopEmbeddedMongo();
    }

    @Before
    public void setUp() throws Exception {
        RestAssured.port = 8080;
        setUpMockMvc(webApplicationContext);
    }

    @Test
    public void registrationRequestShouldRegisterNewUser() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "email@email.com",
                "alamakota",
                "Michael",
                "Jackson",
                new HashSet<>(Collections.singletonList(Technology.KAFKA)),
                Department.WIEIT,
                FieldOfStudy.COMPUTER_SCIENCE
        );

        String postRequestInJSON = convertObjectToJSONFormat(request);

        MvcResult x = performPostWithJSON("/users", postRequestInJSON)
                .andExpect(status().isCreated()).andReturn();
//                .andExpect(jsonPath("$.id", notNullValue()))
//                .andExpect(jsonPath("$.token", notNullValue()));

        System.out.println("@@@@@@@@@@@");
        System.out.println(x.getResponse());
        System.out.println("@@@@@@@@@@@");
    }

    @Test
    public void registrationEndpointShouldReturn406IfUserTryToRegisterOneMoreTime() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "email@email.com",
                "alamakota",
                "Michael",
                "Jackson",
                new HashSet<>(Collections.singletonList(Technology.KAFKA)),
                Department.WIEIT,
                FieldOfStudy.COMPUTER_SCIENCE
        );

        String postRequestInJSON = convertObjectToJSONFormat(request);

        performPostWithJSON("/users", postRequestInJSON).andExpect(status().isCreated());

        Thread.sleep(2000);

        performPostWithJSON("/users", postRequestInJSON).andExpect(status().isNotAcceptable());
    }

    @Test
    public void registrationEndpointShouldReturn406IfDataProvidedByUserWasInvalid() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "",
                "alamakota",
                "Michael",
                "Jackson",
                new HashSet<>(Collections.singletonList(Technology.KAFKA)),
                Department.WIEIT,
                FieldOfStudy.COMPUTER_SCIENCE
        );

        String postRequestInJSON = convertObjectToJSONFormat(request);

        performPostWithJSON("/users", postRequestInJSON).andExpect(status().isNotAcceptable());
    }

    @Test
    public void authenticationEndpointShouldReturn200AndOAuthToken() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "email@email.com",
                "alamakota",
                "Michael",
                "Jackson",
                new HashSet<>(Collections.singletonList(Technology.KAFKA)),
                Department.WIEIT,
                FieldOfStudy.COMPUTER_SCIENCE
        );

        String postRequestInJSON = convertObjectToJSONFormat(request);

        performPostWithJSON("/users", postRequestInJSON).andExpect(status().isCreated());

        UserAuthenticationRequest authenticationRequest = new UserAuthenticationRequest(
                "email@email.com",
                "alamakota"
        );

        String authenticationRequestInJson = convertObjectToJSONFormat(authenticationRequest);

        performPostWithJSON("/users/auth", authenticationRequestInJson)
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", notNullValue()))
//                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    public void authenticationEndpointShouldReturn406IfCredentialsWasInvalid() throws Exception {

    }

}
