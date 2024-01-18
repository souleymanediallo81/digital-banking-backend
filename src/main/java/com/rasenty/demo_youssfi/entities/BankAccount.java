package com.rasenty.demo_youssfi.entities;


import com.rasenty.demo_youssfi.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data @AllArgsConstructor @NoArgsConstructor
public abstract class BankAccount {
    @Id
    private String accountId;
    private double balance;
    private Date createdAt;
    private String currency;
    @ManyToOne
    private Customer customer;
    private AccountStatus status;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;

}
