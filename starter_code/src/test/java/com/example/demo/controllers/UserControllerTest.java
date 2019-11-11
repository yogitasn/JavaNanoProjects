package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class UserControllerTest {

    private UserController userController;



    private UserRepository userRepository=mock(UserRepository.class);

    private CartRepository cartRepository=mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder=mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
         userController=new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
        TestUtils.injectObjects(userController,"BCryptPasswordEncoder",bCryptPasswordEncoder);
    }
    @Test
    public void create_user_happy_path(){
        Logger logger = LoggerFactory.getLogger("splunk.logger");
        logger.info("This is a test");
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisishashed");
        CreateUserRequest r=new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User u=response.getBody();

        assertNotNull(u);
        assertEquals(0,u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisishashed",u.getPassword());

    }

    @Test
    public void test_incorrect_password(){

        CreateUserRequest r=new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testP");
        r.setConfirmPassword("testP");
        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());


    }

    @Test
    public void test_different_password(){

        CreateUserRequest r=new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword1");
        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());


    }

}
