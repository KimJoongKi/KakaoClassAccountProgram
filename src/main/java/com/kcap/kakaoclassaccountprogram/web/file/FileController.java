package com.kcap.kakaoclassaccountprogram.web.file;

import com.kcap.kakaoclassaccountprogram.service.file.FileService;
import com.kcap.kakaoclassaccountprogram.web.file.dto.FileDto;
import com.kcap.kakaoclassaccountprogram.web.file.form.FileForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    @Autowired
    MessageSource messageSource;

    private final FileService fileService;

    @RequestMapping("/test")
    @ResponseBody
    public String uploadTest() {
        return "test";
    }

    @GetMapping("/upload")
    public String updateView(@ModelAttribute("file") FileForm form) {
        return "file/upload";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute("file") FileForm form, BindingResult bindingResult) throws IOException {

        for (MultipartFile uploadFile : form.getUploadFiles()) {
            String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
            if (!messageSource.getMessage("xlsx", null, null).equals(extension)) {
                bindingResult.reject("xlsxExtention", null);
                return "file/upload";
            }
        }

        for (MultipartFile uploadFile : form.getUploadFiles()) {
            XSSFWorkbook excel = new XSSFWorkbook(uploadFile.getInputStream());
            String sheetName = excel.getSheetName(0);
            XSSFRow row = excel.getSheetAt(0).getRow(0);
            XSSFCell cell = row.getCell(1);

            if (!(sheetName.equals("카카오뱅크 거래내역") && cell.toString().equals("카카오뱅크 거래내역"))) {
                bindingResult.reject("kakaoExcelMessage", null);
                return "file/upload";
            }
        }

        FileDto fileDto = FileDto.builder().form(form).build();
        fileService.insertExcelData(fileDto);

        return "file/upload";
    }
}
