package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CartControllerTest {

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

    }

    @Test
    public void create_cart_happy_path(){

        Cart cart = new Cart();
        cart.setId((long)1);
        cart.setItems(new ArrayList<>());
        cart.setTotal(new BigDecimal(1));
        User user = new User();
        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");
        user.setCart(cart);
        Item item = new Item();
        item.setId((long)1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));
        given(userRepository.findByUsername(any())).willReturn(user);
        given(itemRepository.findById(any())).willReturn(Optional.of(item));

        ModifyCartRequest r1=new ModifyCartRequest();
        r1.setUsername(user.getUsername());
        r1.setItemId(item.getId());
        r1.setQuantity(1);
        final ResponseEntity<Cart> response1 = cartController.addTocart(r1);
        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());

        Cart c=response1.getBody();
        assertEquals("Item", c.getItems().get(0).getName());
        assertEquals("Description", c.getItems().get(0).getDescription());
    }

    @Test
    public void remove_cart_happy_path(){

        Cart cart = new Cart();
        cart.setId((long)1);
        cart.setItems(new ArrayList<>());
        cart.setTotal(new BigDecimal(1));
        User user = new User();
        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");
        user.setCart(cart);
        Item item = new Item();
        item.setId((long)1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));
        given(userRepository.findByUsername(any())).willReturn(user);
        given(itemRepository.findById(any())).willReturn(Optional.of(item));

        ModifyCartRequest r1=new ModifyCartRequest();
        r1.setUsername(user.getUsername());
        r1.setItemId(item.getId());
        r1.setQuantity(1);
        final ResponseEntity<Cart> response1 = cartController.removeFromcart(r1);
        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());

        Cart c=response1.getBody();
    }

}
