package com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
public class TransactionHistoryId implements Serializable {

    @Id
    private LocalDateTime date;
    @Id
    private String contents;

}
