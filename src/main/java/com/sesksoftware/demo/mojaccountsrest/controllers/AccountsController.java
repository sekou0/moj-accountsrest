package com.sesksoftware.demo.mojaccountsrest.controllers;

import com.sesksoftware.demo.mojaccountsrest.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {


    private Map<Long, Account> accountMap = new HashMap<>();

    @RequestMapping(path = "/json", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Account>> getAllAcounts() {

        return new ResponseEntity<>(this.getAccounts(), HttpStatus.OK);
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(new ArrayList<>(accountMap.values()));
    }

    public Account addAccount(Account account) throws IllegalArgumentException {

        if(account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        accountMap.put(account.getAccountId(), account);

        return account;

    }
}
