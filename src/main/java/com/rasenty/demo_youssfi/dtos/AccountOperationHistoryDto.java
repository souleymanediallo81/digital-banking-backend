package com.rasenty.demo_youssfi.dtos;


import lombok.Data;

import java.util.List;

@Data
public class AccountOperationHistoryDto {
    private String accountId;
    private double balance;
    private String accountType;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private List<AccountOperationDto> accountOperationDtos;

}
