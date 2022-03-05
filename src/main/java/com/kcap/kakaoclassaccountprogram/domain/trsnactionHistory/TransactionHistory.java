package com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@IdClass(TransactionHistoryId.class)
@Getter
@NoArgsConstructor
@Entity
@ToString
public class TransactionHistory {

    @Id @DateTimeFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    private LocalDateTime date;
    @Id
    private String contents;
    private String division;
    private int price;
    private int afterBalance;
    private String memo;
    private boolean normal;
    private Long fileUploadId;

    @Builder
    public TransactionHistory(Map<String, Object> map) {
        this.date = LocalDateTime.parse(map.get("date").toString(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.contents = map.get("contents").toString();
        this.division = map.get("division").toString();
        this.price = Integer.parseInt(map.get("price").toString());
        this.afterBalance = Integer.parseInt(map.get("afterBalance").toString());
        this.memo = map.get("memo").toString();
        this.normal = (boolean) map.get("normal");
        this.fileUploadId = Long.parseLong(map.get("fileUploadId").toString());
    }

}
