package com.rasenty.demo_youssfi.web;


import com.rasenty.demo_youssfi.dtos.CustomerDto;
import com.rasenty.demo_youssfi.exceptions.CustomerNostFoundException;
import com.rasenty.demo_youssfi.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDto>getCustomers(){
        return bankAccountService.customers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomerById(@PathVariable(name = "id") Long customerId) throws CustomerNostFoundException {
        return bankAccountService.getCustomerById(customerId);
    }

    @GetMapping("/customers/search")
    public List<CustomerDto> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomer("%"+keyword+"%");
    }

    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto){
        customerDto.setId(id);
        return bankAccountService.updateCustomer(customerDto);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

    @PostMapping("/customers")
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto){
        return bankAccountService.saveCustomer(customerDto);
    }
}
