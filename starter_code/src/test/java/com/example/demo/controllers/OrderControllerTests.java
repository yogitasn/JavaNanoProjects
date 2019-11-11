package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class OrderControllerTests {

    private OrderController orderController;

    private UserRepository userRepository=mock(UserRepository.class);

    private OrderRepository orderRepository=mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);


    }

    @Test
    public void submit_order_happy_path() {

        Cart cart = new Cart();
        cart.setId((long) 1);
        cart.setItems(new ArrayList<>());
        cart.setTotal(new BigDecimal(1));
        User user = new User();
        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");
        user.setCart(cart);
        Item item = new Item();
        item.setId((long) 1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));
        given(userRepository.findByUsername(any())).willReturn(user);

        final ResponseEntity<UserOrder> response1 = orderController.submit("Username");
        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());

    }


    @Test
    public void get_Orders_For_User_happy_path() {

        Cart cart = new Cart();
        cart.setId((long) 1);
        cart.setItems(new ArrayList<>());
        cart.setTotal(new BigDecimal(1));
        User user = new User();
        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");
        user.setCart(cart);
        Item item = new Item();
        item.setId((long) 1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));
        given(userRepository.findByUsername(any())).willReturn(user);

        final ResponseEntity<List<UserOrder>> response1 = orderController.getOrdersForUser("Username");
        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());

    }
}
