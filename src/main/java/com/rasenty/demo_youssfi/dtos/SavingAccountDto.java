package com.rasenty.demo_youssfi.dtos;


import com.rasenty.demo_youssfi.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class SavingAccountDto extends BankAccountDto{
    private String accountId;
    private double balance;
    private Date createdAt;
    private CustomerDto customer;
    private AccountStatus status;
    private double interestRate;
}
