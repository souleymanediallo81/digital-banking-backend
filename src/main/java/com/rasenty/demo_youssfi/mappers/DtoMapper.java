package com.rasenty.demo_youssfi.mappers;

import com.rasenty.demo_youssfi.dtos.AccountOperationDto;
import com.rasenty.demo_youssfi.dtos.CurrentAccountDto;
import com.rasenty.demo_youssfi.dtos.CustomerDto;
import com.rasenty.demo_youssfi.dtos.SavingAccountDto;
import com.rasenty.demo_youssfi.entities.AccountOperation;
import com.rasenty.demo_youssfi.entities.CurrentAccount;
import com.rasenty.demo_youssfi.entities.Customer;
import com.rasenty.demo_youssfi.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DtoMapper {

        public CustomerDto fromCustomer(Customer customer){
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerDto);
            return customerDto;
        }

        public Customer fromCustomerDto(CustomerDto customerDto){
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDto, customer);

            return customer;
        }

    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation, accountOperationDto);
        return accountOperationDto;
    }

        public SavingAccountDto fromSavingAccount(SavingAccount savingAccount){
            SavingAccountDto savingAccountDto = new SavingAccountDto();
            BeanUtils.copyProperties(savingAccount, savingAccountDto);
            savingAccountDto.setCustomer(fromCustomer(savingAccount.getCustomer()));
            savingAccountDto.setType(savingAccount.getClass().getSimpleName());
            return savingAccountDto;
        }

        public SavingAccount fromSavingAccountDto(SavingAccountDto savingAccountDto){
            SavingAccount savingAccount = new SavingAccount();
            BeanUtils.copyProperties(savingAccountDto, savingAccount);
            savingAccount.setCustomer(fromCustomerDto(savingAccountDto.getCustomer()));


            return savingAccount;
        }

    public CurrentAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount, currentAccountDto);
        currentAccountDto.setCustomer(fromCustomer(currentAccount.getCustomer()));
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDto;
    }

    public CurrentAccount fromSavingAccountDto(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto, currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentAccountDto.getCustomer()));

        return currentAccount;
    }

}
