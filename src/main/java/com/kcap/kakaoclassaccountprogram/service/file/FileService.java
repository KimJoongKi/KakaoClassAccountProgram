package com.kcap.kakaoclassaccountprogram.service.file;

import com.kcap.kakaoclassaccountprogram.domain.file.FileUpload;
import com.kcap.kakaoclassaccountprogram.domain.file.FileUploadRepository;
import com.kcap.kakaoclassaccountprogram.web.file.dto.FileDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileUploadRepository fileUploadRepository;

    public void insertExcelData(FileDto fileDto) {
        LocalDateTime updateDateTime = fileDto.getUploadDateTime();
        log.info("updateDateTime = {}", updateDateTime);
        List excelUploadList = new ArrayList();

        fileDto.getFileList().forEach(file -> {
            Map<String, Object> map = new HashMap<>();
            String filename = file.getOriginalFilename();
            log.info("filename = {}", filename);
            map.put("filename", filename);
            map.put("uploadDataTime", updateDateTime);

            FileUpload fileUpload = FileUpload
                    .builder()
                    .map(map)
                    .build();

            excelUploadList.add(fileUpload);
        });

        fileUploadRepository.saveAll(excelUploadList);

        // TODO: 2022/03/03 파일 데이터 입력
    }
}

