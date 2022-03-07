package com.kcap.kakaoclassaccountprogram.web.transactionhistory.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TransactionHistorySearchForm {

    private String contents;
    private String startDateTime;
    private String endDateTime;

    @Builder
    public TransactionHistorySearchForm(Map map) {
        this.contents = map.get("contents").toString();
        this.startDateTime = ((LocalDateTime) map.get("startDateTime")).toLocalDate().format(DateTimeFormatter.ISO_DATE);
        this.endDateTime = ((LocalDateTime) map.get("endDateTime")).toLocalDate().format(DateTimeFormatter.ISO_DATE);
    }
}
