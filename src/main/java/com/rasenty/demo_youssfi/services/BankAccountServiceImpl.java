package com.rasenty.demo_youssfi.services;


import com.rasenty.demo_youssfi.dtos.*;
import com.rasenty.demo_youssfi.entities.*;
import com.rasenty.demo_youssfi.enums.AccountStatus;
import com.rasenty.demo_youssfi.enums.OperationType;
import com.rasenty.demo_youssfi.exceptions.BalanceNotSufficientException;
import com.rasenty.demo_youssfi.exceptions.BankAccountNotFoundException;
import com.rasenty.demo_youssfi.exceptions.CustomerNostFoundException;
import com.rasenty.demo_youssfi.mappers.DtoMapper;
import com.rasenty.demo_youssfi.repositories.AccountOperationRepository;
import com.rasenty.demo_youssfi.repositories.BankAccountRepository;
import com.rasenty.demo_youssfi.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
     private DtoMapper dtoMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = dtoMapper.fromCustomerDto(customerDto);
        return dtoMapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer customer = dtoMapper.fromCustomerDto(customerDto);
        return dtoMapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDto> customers() {
        List<CustomerDto> customersDto = customerRepository.findAll()
                .stream().map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customersDto;
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) throws CustomerNostFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNostFoundException("Customer not Found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public BankAccountDto getBankAccountById(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null)
            throw new BankAccountNotFoundException("Bank account not Found !");
        if(bankAccount instanceof SavingAccount){
            return dtoMapper.fromSavingAccount((SavingAccount)bankAccount);
        }else{
            return dtoMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        }

    }

    @Override
    public List<BankAccountDto> bankAccountList(){
        List<BankAccountDto> list = bankAccountRepository.findAll().stream().map(
                bankAccount -> {
                    if (bankAccount instanceof CurrentAccount) {
                        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                        return dtoMapper.fromCurrentAccount(currentAccount);
                    } else {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        return dtoMapper.fromSavingAccount(savingAccount);
                    }
                }
        ).collect(Collectors.toList());

        return list;
    }

    @Override
    public SavingAccountDto saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNostFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNostFoundException("Customer not Found !"));
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setCustomer(customer);
        savingAccount.setBalance(initialBalance);
        savingAccount.setAccountId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setInterestRate(interestRate);

        return dtoMapper.fromSavingAccount(bankAccountRepository.save(savingAccount));

    }

    @Override
    public CurrentAccountDto saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNostFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNostFoundException("Customer not Found !");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setCustomer(customer);
        currentAccount.setBalance(initialBalance);
        currentAccount.setAccountId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setOvertDraft(overDraft);

        return dtoMapper.fromCurrentAccount(bankAccountRepository.save(currentAccount));

    }


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found !"));
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setDateOperation(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmountOperation(amount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found !"));
        AccountOperation accountOperation = new AccountOperation();

        accountOperation.setDateOperation(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
       // accountOperation.setDateOperation(new Date());
        //accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmountOperation(amount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfert(String accountIdScource, String accountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdScource,amount, "Transfer to"+accountIdDest);
        credit(accountIdDest,amount,"Transfer from "+accountIdScource);

    }

    @Override
    public AccountOperationHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        Page<AccountOperation> accountOperations =
                accountOperationRepository.findByBankAccountAccountId(accountId, PageRequest.of(page,size));
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null){
            throw new BankAccountNotFoundException("bank account not found !");
        }
        AccountOperationHistoryDto accountHistoryDto = new AccountOperationHistoryDto();
        accountHistoryDto.setAccountId(bankAccount.getAccountId());
        //accountHistoryDto.setAccountType();

        accountHistoryDto.setBalance(bankAccount.getBalance());

        List<AccountOperationDto> listoperationDto = accountOperations.getContent()
                .stream()
                .map(accOp -> dtoMapper.fromAccountOperation(accOp)).collect(Collectors.toList());
         //accountHistoryDto.setAccountId(bankAccount.getAccountId());
        accountHistoryDto.setAccountOperationDtos(listoperationDto);
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(accountOperations.getTotalPages());


        return accountHistoryDto;
    }

    @Override
    public List<CustomerDto> searchCustomer(String keyword) {
        List<CustomerDto> customersDto = customerRepository.searchCustomer(keyword).stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customersDto;
    }

}
