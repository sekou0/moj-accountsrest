package com.sesksoftware.demo.mojaccountsrest.controllers;

import com.sesksoftware.demo.mojaccountsrest.domain.Account;
import com.sesksoftware.demo.mojaccountsrest.domain.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

        Account existingAccount = accountMap.get(account.getAccountId());

        if(existingAccount != null) {
            throw new IllegalArgumentException("Account id already exists");
        }

        if(StringUtils.isEmpty(account.getFirstName()) ||
            StringUtils.isEmpty(account.getLastName()) ||
            StringUtils.isEmpty(account.getAccountNumber())) {
            throw new IllegalArgumentException("First Name, Last Name and Account NUmber must be completed");
        }

        Long nextAccountId = 1L;

        Comparator<Account> comparator = Comparator.comparingLong(Account::getAccountId);

        Optional<Account> maxIdAccount = accountMap.values().stream().max(comparator);

        if(maxIdAccount.isPresent()) {

            nextAccountId = (maxIdAccount.get().getAccountId().longValue() + 1);
        }

        account.setAccountId(nextAccountId);

        accountMap.put(account.getAccountId(), account);

        return account;

    }


}
