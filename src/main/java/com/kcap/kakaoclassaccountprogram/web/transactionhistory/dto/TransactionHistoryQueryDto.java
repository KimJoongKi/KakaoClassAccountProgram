package com.kcap.kakaoclassaccountprogram.web.transactionhistory.dto;

import com.kcap.kakaoclassaccountprogram.utils.CommonUtils;
import com.kcap.kakaoclassaccountprogram.web.transactionhistory.form.TransactionHistorySearchForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Getter
@NoArgsConstructor
public class TransactionHistoryQueryDto {
    private String contents;
    private LocalDateTime startLocalDate;
    private LocalDateTime endLocalDate;

    @Builder
    public TransactionHistoryQueryDto(TransactionHistorySearchForm form) {
        this.contents = form.getContents();
        this.startLocalDate = LocalDate.parse(form.getStartDateTime()).atTime(LocalTime.MIN);
        this.endLocalDate = LocalDate.parse(form.getEndDateTime()).atTime(LocalTime.MAX);
    }

    public String getLikeContents() {
        return "%" + this.contents + "%";
    }
}
