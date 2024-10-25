package com.banking.transfer_service.feign;

import com.banking.transfer_service.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "http://localhost:8080/api/accounts")
public interface AccountClient {

    @GetMapping("/number/{accountNumber}")
    Account getAccountByNumber(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/{accountNumber}")
    void updateAccount(@PathVariable("accountNumber") String accountNumber, @RequestBody Account account);
}
