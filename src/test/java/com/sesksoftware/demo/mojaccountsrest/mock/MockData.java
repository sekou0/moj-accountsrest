package com.sesksoftware.demo.mojaccountsrest.mock;

import com.sesksoftware.demo.mojaccountsrest.domain.Account;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static final List<Account> getMockAccounts() {
        List<Account> accountList = new ArrayList<>();

        Account account = new Account();
        account.setAccountId(1L);
        account.setFirstName("Tony");
        account.setLastName("Stark");
        account.setAccountNumber("IRN-493-2030");

        accountList.add(account);

        account = new Account();
        account.setAccountId(2L);
        account.setFirstName("Bruce");
        account.setLastName("Banner");
        account.setAccountNumber("HLK-493-2830");

        accountList.add(account);

        account = new Account();
        account.setAccountId(3L);
        account.setFirstName("Steve");
        account.setLastName("Rogers");
        account.setAccountNumber("CAP-124-2030");

        accountList.add(account);

        return accountList;
    }

    public static final Account getMockAccountRequest() {

        Account account = new Account();
        account.setFirstName("Natasha");
        account.setLastName("Romanov");
        account.setAccountNumber("WID-820-2330");

        return account;
    }


}
