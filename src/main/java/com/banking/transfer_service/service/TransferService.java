package com.banking.transfer_service.service;

import com.banking.transfer_service.feign.AccountClient;
import com.banking.transfer_service.model.Account;
import com.banking.transfer_service.model.Transfer;
import com.banking.transfer_service.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;

@Service
public class TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountClient accountClient;

    public String performTransfer(String sourceAccountNumber, String destinationAccountNumber, Double amount) {
        logger.info("Iniciando transferencia desde {} hacia {} por un monto de {}", sourceAccountNumber, destinationAccountNumber, amount);

        // Verificar si la cuenta de origen tiene fondos suficientes
        Account sourceAccount = accountClient.getAccountByNumber(sourceAccountNumber);
        if (sourceAccount.getBalance() < amount) {
            logger.error("Fondos insuficientes en la cuenta de origen: {}", sourceAccountNumber);
            throw new IllegalArgumentException("Fondos insuficientes en la cuenta de origen.");
        }

        // Verificar si la cuenta de destino existe
        Account destinationAccount = accountClient.getAccountByNumber(destinationAccountNumber);
        if (destinationAccount == null) {
            logger.error("La cuenta de destino no existe: {}", destinationAccountNumber);
            throw new IllegalArgumentException("La cuenta de destino no existe.");
        }

        // Actualizar saldos de las cuentas
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        accountClient.updateAccount(sourceAccountNumber, sourceAccount);
        accountClient.updateAccount(destinationAccountNumber, destinationAccount);

        // Registrar la transferencia
        Transfer transfer = new Transfer();
        transfer.setSourceAccountNumber(sourceAccountNumber);
        transfer.setDestinationAccountNumber(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setTransferDate(LocalDateTime.now());
        transferRepository.save(transfer);

        logger.info("Transferencia realizada con éxito desde {} hacia {} por un monto de {}", sourceAccountNumber, destinationAccountNumber, amount);

        return "Transferencia realizada con éxito.";
    }
}