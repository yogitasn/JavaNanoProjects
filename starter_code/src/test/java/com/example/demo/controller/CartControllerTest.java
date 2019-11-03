package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private UserController userController;
    private BCryptPasswordEncoder bCryptPasswordEncoder=mock(BCryptPasswordEncoder.class);


    private CartController cartController;
    private UserRepository userRepository=mock(UserRepository.class);
    private CartRepository cartRepository=mock(CartRepository.class);
    private ItemRepository itemRepository=mock(ItemRepository.class);


    @Before
    public void setUp(){
        cartController=new CartController();
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"cartRepository",cartRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);

        userController=new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
        TestUtils.injectObjects(userController,"BCryptPasswordEncoder",bCryptPasswordEncoder);

    }
    @Test
    public void create_add_to_cart_path(){
        CreateUserRequest r=new CreateUserRequest();

        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(r);
        User u=response.getBody();
        Cart c=new Cart();
        c.setId(1L);
        Item i=new Item();
        i.setId(1L);
        i.setDescription("First Item");
        i.setName("Bag");
        BigDecimal bd1 =
                new BigDecimal("124567890.0987654321");

        i.setPrice(bd1);
        i.setName("Gucci");
        List<Item> item;
        //item.add(i);
//        c.setItems(item);
        c.setUser(u);
        ModifyCartRequest r1=new ModifyCartRequest();
        r1.setUsername(u.getUsername());

       final ResponseEntity<Cart> cartResponse = cartController.addTocart(r1);

        assertNotNull(cartResponse);
        assertEquals(200, cartResponse.getStatusCodeValue());

       // Cart c=cartResponse.getBody();

        //assertNotNull(c);
        assertEquals("test", c.getUser().getUsername());
    }
}
