package com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionHistoryVO {
    private LocalDateTime date;
    private String contents;
    private String division;
    private int price;
    private int afterBalance;
    private String memo;
}
