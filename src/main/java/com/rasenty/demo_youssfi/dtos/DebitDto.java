package com.rasenty.demo_youssfi.dtos;


import lombok.Data;

@Data
public class DebitDto {
    private String accountId;
    private double amount;
    private String description;
}
