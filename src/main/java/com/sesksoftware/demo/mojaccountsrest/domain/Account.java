package com.sesksoftware.demo.mojaccountsrest.domain;

public class Account {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String accountNumber;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
