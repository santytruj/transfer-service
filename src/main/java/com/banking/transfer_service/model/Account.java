package com.banking.transfer_service.model;


import lombok.Data;

@Data
public class Account {
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Double balance;
}