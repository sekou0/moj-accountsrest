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

    /***
     * Get all the account records
     * @return a ResponseEntity containing the list of accounts
     */
    @RequestMapping(path = "/json", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Account>> getAllAccounts() {

        return new ResponseEntity<>(this.getAccounts(), HttpStatus.OK);
    }

    /***
     * Adds an account request to the accounts list
     * @param account - instance to be added
     * @return - fullu hydrated account object with new account id
     * @throws Exception for Bad requests data
     */
    @RequestMapping(path = "/json", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<MessageResponse> addAccountRequest(
            @RequestBody Account account) throws Exception {

        Account newAccount = this.addAccount(account);

        return new ResponseEntity<>(new MessageResponse("account has been successfully added"), HttpStatus.CREATED);
    }

    /***
     * deletes the account from the list
     * @param accountId id of the account to remove
     * @return ResponseEntity with message response
     * @throws Exception
     */
    @RequestMapping(path = "/json/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<MessageResponse> deleteAccountRequest(
            @PathVariable(name = "id") String accountId ) throws Exception {


        Long accountIdLong = null;

        try {
            accountIdLong = Long.parseLong(accountId);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id Must be a valid number");

        }

        removeAccount(accountIdLong);

        return new ResponseEntity<>(new MessageResponse("account successfully deleted"), HttpStatus.MOVED_PERMANENTLY);
    }

    /**
     * Exception handler
     * @param e Illegal Arguement Exception
     * @return response entity with the message response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> illegalArgumentException(IllegalArgumentException e) {

        return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Get unmodifiable list of accounts from the map
     * @return unmodifiable list
     */
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(new ArrayList<>(accountMap.values()));
    }

    /**
     * add the account
     * @param account account to add to the list
     * @return the new account
     * @throws IllegalArgumentException
     */
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
            throw new IllegalArgumentException("First Name, Last Name and Account Number must be completed");
        }

        Optional<Account> duplicateAccountNumber = accountMap.values()
                .stream()
                .filter(savedAccount -> savedAccount.getAccountNumber().equalsIgnoreCase(account.getAccountNumber()))
                .findFirst();

        if(duplicateAccountNumber.isPresent()) {
            throw new IllegalArgumentException(String.format("Account Number %s already exists", account.getAccountNumber()));
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

    /**
     * removes the account from list
     * @param accountId id to remove
     * @return the removed account
     * @throws Exception
     */
    public Account removeAccount(Long accountId) throws Exception {

        Account removedAccount =  accountMap.remove(accountId);

        if(removedAccount == null) {

            throw new IllegalArgumentException(String.format("Account Id: %d does not exist", accountId ));
        }

        return removedAccount;
    }


}
