package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
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

public class ItemControllerTests {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void get_item() {

        Item item = new Item();
        item.setId((long) 1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        final ResponseEntity<Item> response1 = itemController.getItemById(item.getId());

        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());

    }

    @Test
    public void get_item_by_name() {

        Item item = new Item();
        item.setId((long) 1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));
        List<Item> items=new ArrayList<>();
        items.add(item);
        given(itemRepository.findByName(any())).willReturn(items);
        final ResponseEntity<List<Item>> response1 = itemController.getItemsByName(item.getName());

        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());

    }

    @Test
    public void get_items() {

        Item item = new Item();
        item.setId((long) 1);
        item.setName("Item");
        item.setDescription("Description");
        item.setPrice(new BigDecimal(1));

        Item item1 = new Item();
        item1.setId((long) 2);
        item1.setName("Item1");
        item1.setDescription("Description1");
        item1.setPrice(new BigDecimal(2));
        List<Item> items=new ArrayList<>();
        items.add(item);
        items.add(item1);
        given(itemRepository.findAll()).willReturn(items);
        final ResponseEntity<List<Item>> response1 = itemController.getItems();

        List<Item> returnedItem=response1.getBody();
        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());
        assertEquals("Item1",returnedItem.get(1).getName());
    }

}
