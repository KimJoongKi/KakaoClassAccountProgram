package com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
