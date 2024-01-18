package com.rasenty.demo_youssfi.dtos;


import com.rasenty.demo_youssfi.entities.AccountOperation;
import com.rasenty.demo_youssfi.entities.Customer;
import com.rasenty.demo_youssfi.enums.AccountStatus;
import lombok.Data;


import java.util.Date;

@Data
public class CurrentAccountDto extends BankAccountDto {

    private String accountId;
    private double balance;
    private Date createdAt;
    private CustomerDto customer;
    private AccountStatus status;
    private double overtDraft;

}
