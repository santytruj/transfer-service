package com.banking.transfer_service.repository;

import com.banking.transfer_service.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}