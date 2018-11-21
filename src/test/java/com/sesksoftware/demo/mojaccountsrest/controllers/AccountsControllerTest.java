package com.sesksoftware.demo.mojaccountsrest.controllers;

import com.sesksoftware.demo.mojaccountsrest.mock.MockData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AccountsController accountsController;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(accountsController).build();

        MockData.getMockAccounts().stream().forEach(account -> accountsController.addAccount(account));
    }


    @Test
    public void getAllAccountsTest() throws Exception {

        mockMvc.perform(get("/accounts/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].accountId", is(1)));

    }


}
