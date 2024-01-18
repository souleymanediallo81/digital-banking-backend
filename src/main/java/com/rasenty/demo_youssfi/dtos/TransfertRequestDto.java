package com.rasenty.demo_youssfi.dtos;

import lombok.Data;

@Data
public class TransfertRequestDto {
    private String accountId;
    private double amount;
    private String accountDest;
    //private String accountDest;
}
