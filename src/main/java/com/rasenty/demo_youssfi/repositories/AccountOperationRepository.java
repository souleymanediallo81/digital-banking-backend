package com.rasenty.demo_youssfi.repositories;


import com.rasenty.demo_youssfi.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    //public Page<AccountOperation> findByBankAccountId(String id, Pageable pageable);
    Page<AccountOperation> findByBankAccountAccountId(String id, Pageable pageable);
    public List<AccountOperation> findByBankAccountAccountId(String id);
}
