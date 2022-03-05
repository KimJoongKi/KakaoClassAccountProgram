package com.kcap.kakaoclassaccountprogram.domain.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
public class FileUpload {

    @Id @DateTimeFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    @GeneratedValue
    private Long id;
    private String filename;
    private LocalDateTime uploadDateTime;

    @Builder
    public FileUpload(Map<String, Object> map) {
        this.filename = map.get("filename").toString();
        this.uploadDateTime = (LocalDateTime) map.get("uploadDateTime");
    }

}
