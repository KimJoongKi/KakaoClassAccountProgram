package com.kcap.kakaoclassaccountprogram.domain.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
public class FileUpload {

    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private LocalDateTime uploadDateTime;

    @Builder
    public FileUpload(Map<String, Object> map) {
        this.filename = map.get("filename").toString();
        this.uploadDateTime = (LocalDateTime) map.get("uploadDataTime");
    }

}
