package com.rasenty.demo_youssfi.dtos;

import com.rasenty.demo_youssfi.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date dateOperation;
    private double amountOperation;
    private String description;
    private OperationType operationType;
}
