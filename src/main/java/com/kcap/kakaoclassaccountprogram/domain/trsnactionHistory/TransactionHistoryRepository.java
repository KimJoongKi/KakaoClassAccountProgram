package com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory;

import com.kcap.kakaoclassaccountprogram.web.transactionhistory.dto.TransactionHistoryQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findTransactionHistoriesByDateBetweenAndContentsLikeOrderByDateDesc(LocalDateTime startLocalDate, LocalDateTime endLocalDate, String contents);
}
