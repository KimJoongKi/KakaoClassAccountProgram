package com.kcap.kakaoclassaccountprogram.web.file.dto;

import com.kcap.kakaoclassaccountprogram.web.file.form.FileForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FileDto {

    private List<MultipartFile> fileList = new ArrayList<MultipartFile>();
    private LocalDateTime uploadDateTime;

    @Builder
    public FileDto(FileForm form) {
        this.fileList = form.getUploadFiles();
        this.uploadDateTime = LocalDateTime.now();
    }

}
