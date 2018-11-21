package com.sesksoftware.demo.mojaccountsrest.controllers;

import com.sesksoftware.demo.mojaccountsrest.domain.Account;
import com.sesksoftware.demo.mojaccountsrest.domain.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {


    private Map<Long, Account> accountMap = new HashMap<>();

    @RequestMapping(path = "/json", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Account>> getAllAcounts() {

        return new ResponseEntity<>(this.getAccounts(), HttpStatus.OK);
    }

    @RequestMapping(path = "/json", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<MessageResponse> addAccountRequest(
            @RequestBody Account account) throws Exception {

        Account newAccount = this.addAccount(account);

        return new ResponseEntity<>(new MessageResponse("account has been successfully added"), HttpStatus.CREATED);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> illegalArgumentException(IllegalArgumentException e) {

        return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
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
