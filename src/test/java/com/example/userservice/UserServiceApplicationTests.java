package com.example.userservice;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application.properties")
class UserServiceApplicationTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void saveUser() throws URISyntaxException {
        String uri = "http://localhost:8080/api/v1/users";
        User user = User.builder().address("Adrs").age(21).firstName("FN").lastName("LN").build();
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user, User.class);
        //Verify post request succeed
        Assert.assertEquals(201, postResult.getStatusCodeValue());
        //Verify by get request succeed
        ResponseEntity<User> getResult = restTemplate.getForEntity(uri + "/1", User.class);
        Assert.assertEquals(200, getResult.getStatusCodeValue());
        Assert.assertEquals(21, getResult.getBody().getAge().intValue());
    }

    @Test
    public void updateUser() {
        String uri = "http://localhost:8080/api/v1/users";
        User user = User.builder().address("Adrs").age(21).firstName("FN").lastName("LN").build();
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user, User.class);
        UserRequest updateUser = UserRequest.builder().address("Adrs1").build();
        HttpEntity requestBody = new HttpEntity<>(updateUser);
        ResponseEntity<User> patchResult = restTemplate.exchange(uri + "/1", HttpMethod.PUT, requestBody, User.class, new HashMap<>());
        Assert.assertEquals("Adrs1", patchResult.getBody().getAddress());

    }

    @Test
    public void getAllTheUsers() {
        String uri = "http://localhost:8080/api/v1/users";
        User user = User.builder().address("Adrs").age(21).firstName("FN").lastName("LN").build();
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user, User.class);
        ResponseEntity<List> getAllResult = restTemplate.getForEntity(uri, List.class);
        Assert.assertEquals(200, getAllResult.getStatusCodeValue());
        Map userObject = (Map) getAllResult.getBody().get(0);
        Assert.assertTrue(user.getFirstName().equals("FN"));
        Assert.assertTrue(user.getLastName().equals("LN"));
        Assert.assertTrue(user.getAddress().equals("Adrs"));

    }

    @Test
    public void getUserByFirstName() {
        String uri = "http://localhost:8080/api/v1/users";
        User user = User.builder().address("Adrs").age(21).firstName("FN").lastName("LN").build();
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user, User.class);
        ResponseEntity<User> getUserByFirstName = restTemplate.getForEntity(uri + "/first-name/FN", User.class);
        Assert.assertEquals("FN", getUserByFirstName.getBody().getFirstName());
    }

    @Test
    public void updateUserException() {
        String uri = "http://localhost:8080/api/v1/users";
        UserRequest updateUser = UserRequest.builder().address("Adrs1").build();
        HttpEntity requestBody = new HttpEntity<>(updateUser);
        try{
            restTemplate.exchange(uri + "/1", HttpMethod.PUT, requestBody, String.class, new HashMap<>());
        }
        catch(HttpClientErrorException e){
            Assert.assertEquals(404, e.getRawStatusCode());
            Assert.assertEquals("User not found with given id: 1" , e.getResponseBodyAsString());
        }


    }

    @Test
    public void getUserByIdException(){
        String uri = "http://localhost:8080/api/v1/users";
        User user = User.builder().address("Adrs").age(21).firstName("FN").lastName("LN").build();
        ResponseEntity<User> postResult = restTemplate.postForEntity(uri, user, User.class);
        try{
            restTemplate.getForEntity(uri + "/1", User.class);
        }
        catch(HttpClientErrorException e){
            Assert.assertEquals(404, e.getRawStatusCode());
            Assert.assertEquals("User not found with given id: 1" , e.getResponseBodyAsString());
        }



    }


}
