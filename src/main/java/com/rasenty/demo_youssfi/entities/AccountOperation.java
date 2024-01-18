package com.rasenty.demo_youssfi.entities;

import com.rasenty.demo_youssfi.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOperation;
    private double amountOperation;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private OperationType operationType;
    @ManyToOne
    private BankAccount bankAccount;


}
