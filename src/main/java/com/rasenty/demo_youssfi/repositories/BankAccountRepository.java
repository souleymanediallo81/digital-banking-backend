package com.rasenty.demo_youssfi.repositories;

import com.rasenty.demo_youssfi.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
