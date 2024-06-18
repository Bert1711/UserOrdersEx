package com.zaroyan.userordersapi.controllers;

import com.zaroyan.userordersapi.model.Order;
import com.zaroyan.userordersapi.model.User;
import com.zaroyan.userordersapi.servicies.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Zaroyan
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    private static List<User> users;

    @BeforeAll
    public static void setup() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Albert");
        user1.setEmail("albert@albertmail.com");

        Order order1 = new Order();
        order1.setId(1L);
        order1.setItems("MacBook");
        order1.setAmount(10.0);
        order1.setStatus("В ожидании");
        order1.setUser(user1);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setItems("Ferrari");
        order2.setAmount(100.0);
        order2.setStatus("Ожидает оплаты");
        order2.setUser(user1);

        user1.setOrders(Arrays.asList(order1, order2));

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Natali");
        user2.setEmail("natali@natali.com");

        Order order3 = new Order();
        order3.setId(3L);
        order3.setItems("Dyson");
        order3.setAmount(1.0);
        order3.setStatus("Доставлен");
        order3.setUser(user2);

        user2.setOrders(Arrays.asList(order3));

        users = Arrays.asList(user1, user2);
    }

    @Test
    public void testGetAllUsers_UserSummaryView() throws Exception {
        Mockito.when(userService.findAllUsers()).thenReturn(users);

        ResultActions resultActions = mockMvc.perform(get("/api/users/summary")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Albert"))
                .andExpect(jsonPath("$[0].email").value("albert@albertmail.com"))
                .andExpect(jsonPath("$[0].orders").doesNotExist())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Natali"))
                .andExpect(jsonPath("$[1].email").value("natali@natali.com"))
                .andExpect(jsonPath("$[1].orders").doesNotExist());
    }

    @Test
    public void testGetAllUsers_UserDetailsView() throws Exception {
        Mockito.when(userService.findAllUsers()).thenReturn(users);

        ResultActions resultActions = mockMvc.perform(get("/api/users/details")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Albert"))
                .andExpect(jsonPath("$[0].email").value("albert@albertmail.com"))
                .andExpect(jsonPath("$[0].orders").isArray())
                .andExpect(jsonPath("$[0].orders[0].id").value(1))
                .andExpect(jsonPath("$[0].orders[0].items").value("MacBook"))
                .andExpect(jsonPath("$[0].orders[0].amount").value(10.0))
                .andExpect(jsonPath("$[0].orders[0].status").value("В ожидании"))
                .andExpect(jsonPath("$[0].orders[1].id").value(2))
                .andExpect(jsonPath("$[0].orders[1].items").value("Ferrari"))
                .andExpect(jsonPath("$[0].orders[1].amount").value(100.0))
                .andExpect(jsonPath("$[0].orders[1].status").value("Ожидает оплаты"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Natali"))
                .andExpect(jsonPath("$[1].email").value("natali@natali.com"))
                .andExpect(jsonPath("$[1].orders").isArray())
                .andExpect(jsonPath("$[1].orders[0].id").value(3))
                .andExpect(jsonPath("$[1].orders[0].items").value("Dyson"))
                .andExpect(jsonPath("$[1].orders[0].amount").value(1.0))
                .andExpect(jsonPath("$[1].orders[0].status").value("Доставлен"));
    }
}