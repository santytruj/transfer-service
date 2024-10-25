package com.banking.transfer_service.controller;

import com.banking.transfer_service.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public String performTransfer(@RequestParam String sourceAccountNumber,
                                  @RequestParam String destinationAccountNumber,
                                  @RequestParam Double amount) {
        return transferService.performTransfer(sourceAccountNumber, destinationAccountNumber, amount);
    }
}