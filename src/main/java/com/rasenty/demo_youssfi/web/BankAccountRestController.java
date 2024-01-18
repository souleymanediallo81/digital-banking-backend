package com.rasenty.demo_youssfi.web;


import com.rasenty.demo_youssfi.dtos.*;
import com.rasenty.demo_youssfi.exceptions.BalanceNotSufficientException;
import com.rasenty.demo_youssfi.exceptions.BankAccountNotFoundException;
import com.rasenty.demo_youssfi.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{id}")
    public BankAccountDto getBankAccount(@PathVariable(name = "id") String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccountById(accountId);
    }

    @GetMapping("accounts/{accountId}/pageOperations")
    public AccountOperationHistoryDto getAccounthistory(
            @PathVariable String accountId,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name="page", defaultValue  ="0") int page
    ) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }

    @PostMapping("accounts/credit")
    public CreditDto credit(@RequestBody CreditDto creditDto) throws BankAccountNotFoundException {
         this.bankAccountService.credit(creditDto.getAccountId(), creditDto.getAmount(), creditDto.getDescription());
         return creditDto;
    }

    @PostMapping("accounts/debit")
    public DebitDto debit(@RequestBody DebitDto debitDto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDto.getAccountId(), debitDto.getAmount(), debitDto.getDescription());
        return debitDto;
    }

    @PostMapping("accounts/transfert")
    public TransfertRequestDto transfert(@RequestBody TransfertRequestDto transferDto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfert(transferDto.getAccountId(),transferDto.getAccountDest(),transferDto.getAmount());
        return transferDto;
    }
    @GetMapping("accounts")
    public List<BankAccountDto> getAllBankAccount(){
        return bankAccountService.bankAccountList();
    }


}
