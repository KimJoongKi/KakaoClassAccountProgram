package com.kcap.kakaoclassaccountprogram.service.transactionhistory;

import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.TransactionHistory;
import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public List<TransactionHistory> getTransactionHistoryList() {
        return transactionHistoryRepository.findAll();
    }
}
