package com.rasenty.demo_youssfi.services;

import com.rasenty.demo_youssfi.dtos.*;
import com.rasenty.demo_youssfi.entities.CurrentAccount;
import com.rasenty.demo_youssfi.entities.SavingAccount;
import com.rasenty.demo_youssfi.exceptions.BalanceNotSufficientException;
import com.rasenty.demo_youssfi.exceptions.BankAccountNotFoundException;
import com.rasenty.demo_youssfi.exceptions.CustomerNostFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BankAccountService {

    CustomerDto saveCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long customerId);

    List<CustomerDto> customers();

    CustomerDto getCustomerById(Long customerId) throws CustomerNostFoundException;

    BankAccountDto getBankAccountById(String accountId) throws BankAccountNotFoundException;

    List<BankAccountDto> bankAccountList();

    SavingAccountDto saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNostFoundException;

    CurrentAccountDto saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNostFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfert(String accountIdScource, String accountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    AccountOperationHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDto> searchCustomer(String keyword);

    //CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId);

    //SavingAccount saveSavingAccount(double initialBalance, double interesRate, Long customerId);
}
