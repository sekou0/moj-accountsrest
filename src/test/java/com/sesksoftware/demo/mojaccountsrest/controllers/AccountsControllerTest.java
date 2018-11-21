package com.sesksoftware.demo.mojaccountsrest.controllers;

import com.google.gson.Gson;
import com.sesksoftware.demo.mojaccountsrest.domain.Account;
import com.sesksoftware.demo.mojaccountsrest.mock.MockData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AccountsController accountsController;

    private Gson gson = new Gson();

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

    @Test
    public void addAccountTest() throws Exception {

        List<Account> accountList = accountsController.getAccounts();

        assertNotNull(accountList);
        assertEquals(3, accountList.size());

        String json = gson.toJson(MockData.getMockAccountRequest());

        mockMvc.perform(post("/accounts/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("account has been successfully added")));

        accountList = accountsController.getAccounts();

        assertNotNull(accountList);
        assertEquals(4, accountList.size());
    }

    @Test
    public void addAccountNullAccountTest() throws Exception {

        mockMvc.perform(post("/accounts/json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void addAccountFirstNameNullTest() throws Exception {

        Account account = MockData.getMockAccountRequest();

        account.setFirstName(null);

        String json = gson.toJson(account);

        mockMvc.perform(post("/accounts/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("First Name, Last Name and Account Number must be completed")));

    }

    @Test
    public void addAccountLastNameNullTest() throws Exception {

        Account account = MockData.getMockAccountRequest();

        account.setLastName(null);

        String json = gson.toJson(account);

        mockMvc.perform(post("/accounts/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("First Name, Last Name and Account Number must be completed")));

    }

    @Test
    public void addAccountAccountNumberNullTest() throws Exception {

        Account account = MockData.getMockAccountRequest();

        account.setAccountNumber(null);

        String json = gson.toJson(account);

        mockMvc.perform(post("/accounts/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("First Name, Last Name and Account Number must be completed")));

    }

    @Test
    public void addAccountDuplicateAccountTest() throws Exception {

        Account account = MockData.getMockAccounts().get(0);

        String json = gson.toJson(account);

        mockMvc.perform(post("/accounts/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Account id already exists")));

    }

    @Test
    public void deleteAccountTest() throws Exception {

        mockMvc.perform(delete("/accounts/json/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("account successfully deleted")))
                .andExpect(status().isMovedPermanently());
    }
}
