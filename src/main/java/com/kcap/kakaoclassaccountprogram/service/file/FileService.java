package com.kcap.kakaoclassaccountprogram.service.file;

import com.kcap.kakaoclassaccountprogram.domain.file.FileUpload;
import com.kcap.kakaoclassaccountprogram.domain.file.FileUploadRepository;
import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.TransactionHistory;
import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.TransactionHistoryRepository;
import com.kcap.kakaoclassaccountprogram.web.file.dto.FileDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileUploadRepository fileUploadRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private static final Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2} \\d{2}:\\d{2}:\\d{2}");
    private static final Pattern numberPattern = Pattern.compile("[0-9]+");

    public void insertExcelData(FileDto fileDto) {
        fileDto.getFileList().stream().forEach(file -> {
            insertExcelData(file, insertExcelUploadFileData(file));
        });
    }

    private void insertExcelData(MultipartFile file, Long fileUploadId) {
        XSSFWorkbook excel = null;
        try {
            excel = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        transactionHistoryRepository.saveAll(insertSheetData(fileUploadId, excel));
    }

    private List<TransactionHistory> insertSheetData(Long fileUploadId, XSSFWorkbook excel) {
        XSSFSheet sheet = excel.getSheetAt(0);
        XSSFRow row = sheet.getRow(10);
        int lastRowNum = sheet.getLastRowNum();
        int lastCellNum = (int) row.getLastCellNum();

        Matcher matcher = null;
        List<TransactionHistory> list = new ArrayList<>();
        for (int i = 11; i <= lastRowNum; i++) {
            XSSFRow selectRow = sheet.getRow(i);
            Map<String, Object> map = new HashMap<>();
            map.put("normal", true);
            map.put("fileUploadId", fileUploadId);
            for (int j = 1; j <= lastCellNum; j++) {
                XSSFCell cell = selectRow.getCell(j);
                switch (j) {
                    case 1:
                        matcher = datePattern.matcher(cell.getStringCellValue());
                        if (!matcher.find()) {
                            map.put("normal", false);
                        }
                        map.put("date", matcher.group());
                        break;
                    case 2:
                        map.put("division", cell.getStringCellValue());
                        break;
                    case 3:
                        matcher = numberPattern.matcher(cell.getStringCellValue().replaceAll(",",""));
                        if (!matcher.find()) {
                            map.put("normal", false);
                        }
                        map.put("price", matcher.group().replaceAll("[^0-9]", ""));
                        break;
                    case 4:
                        matcher = numberPattern.matcher(cell.getStringCellValue().replaceAll(",",""));
                        if (!matcher.find()) {
                            map.put("normal", false);
                        }
                        map.put("afterBalance", matcher.group().replaceAll("[^0-9]", ""));
                        break;
                    case 6:
                        map.put("contents", cell.getStringCellValue());
                        break;
                    case 7:
                        map.put("memo", cell.getStringCellValue());
                        break;
                }
            }
            list.add(TransactionHistory.builder().map(map).build());
        }
        return list;
    }

    private Long insertExcelUploadFileData(MultipartFile file) {
        Map<String, Object> uploadFileMap = new HashMap<>();
        uploadFileMap.put("filename", file.getOriginalFilename());
        uploadFileMap.put("uploadDateTime", LocalDateTime.now());
        return fileUploadRepository.save(FileUpload.builder().map(uploadFileMap).build()).getId();
    }



}

