package com.rasenty.demo_youssfi;

import com.rasenty.demo_youssfi.dtos.CustomerDto;
import com.rasenty.demo_youssfi.entities.*;
import com.rasenty.demo_youssfi.enums.AccountStatus;
import com.rasenty.demo_youssfi.enums.OperationType;
import com.rasenty.demo_youssfi.exceptions.BalanceNotSufficientException;
import com.rasenty.demo_youssfi.exceptions.BankAccountNotFoundException;
import com.rasenty.demo_youssfi.repositories.AccountOperationRepository;
import com.rasenty.demo_youssfi.repositories.BankAccountRepository;
import com.rasenty.demo_youssfi.repositories.CustomerRepository;
import com.rasenty.demo_youssfi.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoYoussfiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoYoussfiApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository,
                            BankAccountService bankAccountService,
                            CustomerRepository customerRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Bah", "Diallo", "Sow").forEach(name->{
                CustomerDto customer = new CustomerDto();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");

                bankAccountService.saveCustomer(customer);
            });

            customerRepository.findAll().forEach(customer->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setAccountId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()* 9000);
                currentAccount.setCustomer(customer);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setOvertDraft(9000);

                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setAccountId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()* 9000);
                savingAccount.setCustomer(customer);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);


            });

            bankAccountRepository.findAll().forEach(acc-> {
                for (int i = 0; i < 10; i++) {
                    try {
                        bankAccountService.credit(acc.getAccountId(), 1000 + Math.random() * 9000, "Credit");
                    } catch (BankAccountNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        bankAccountService.debit(acc.getAccountId(), 1000 + Math.random() * 1000, "Debit");
                    } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }
                }});

//            customerRepository.findAll().forEach(customer -> {
//                CurrentAccount currentAccount = new CurrentAccount();
//                currentAccount.setAccountId(UUID.randomUUID().toString());
//                currentAccount.setBalance(Math.random()*5000);
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setCustomer(customer);
//                currentAccount.setOvertDraft(8000);
//                currentAccount.setCreatedAt(new Date());
//
//                bankAccountRepository.save(currentAccount);
//
//                SavingAccount savingAccount = new SavingAccount();
//                savingAccount.setAccountId(UUID.randomUUID().toString());
//                savingAccount.setCustomer(customer);
//                savingAccount.setCreatedAt(new Date());
//                savingAccount.setInterestRate(5.5);
//                savingAccount.setBalance(8000);
//                savingAccount.setStatus(AccountStatus.CREATED);
//
//                bankAccountRepository.save(savingAccount);
//
//
//               bankAccountRepository.findAll().forEach(bankAccount -> {
//                   for(int i = 0; i<10; i++){
//                        AccountOperation accountOperation = new AccountOperation();
//                        accountOperation.setBankAccount(bankAccount);
//                        accountOperation.setOperationType(Math.random()<0.5? OperationType.CREDIT: OperationType.DEBIT);
//                        accountOperation.setAmountOperation(Math.random() * 12000);
//                        accountOperation.setDateOperation(new Date());
//
//                        accountOperationRepository.save(accountOperation);
//                   }
//               });
//
//            });
            };}}





