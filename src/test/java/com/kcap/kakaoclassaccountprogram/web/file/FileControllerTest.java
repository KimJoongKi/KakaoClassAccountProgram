package com.kcap.kakaoclassaccountprogram.web.file;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 파일_업로드_테스트() throws Exception {

        File file = new File("src/main/resources/test");  // 테스트 파일경로
        Queue<MockMultipartFile> queue = new LinkedList<MockMultipartFile>();
        File[] files = file.listFiles();
        for (File file1 : files) {
            MockMultipartFile mockMultipartFile =
                    new MockMultipartFile(file1.getName(), new FileInputStream(file1));
            queue.offer(new MockMultipartFile(file1.getName(), new FileInputStream(file1)));
        }

        // 테스트 파일 명에 맞게 변경
        assertEquals("테스트파일.xlsx", queue.poll().getName());
        assertEquals("테스트파일2.xlsx", queue.poll().getName());

    }

    @Test
    public void 카카오뱅크_엑셀파일_검증_테스트() throws IOException {
        File file = new File("src/main/resources/test");  // 테스트 파일경로
        FileInputStream fis = new FileInputStream(file.listFiles()[0]);
        XSSFWorkbook excel = new XSSFWorkbook(fis);;
        String sheetName = excel.getSheetName(0);
        String cellName = getCellDate(excel, 0, 1);
        String accountHolder = getCellDate(excel, 3, 2);
        String accountNumber = getCellDate(excel, 4, 2);
        Pattern pattern = Pattern.compile("([0-9])+");
        Matcher matcher = pattern.matcher(accountNumber);
        matcher.find();

        assertEquals("카카오뱅크 거래내역", sheetName, cellName);
        assertEquals("김중기", accountHolder);
        assertEquals("1826", matcher.group());
    }

    // TODO: 2022/01/07 엑셀파일 데이터 입력 테스트 작업

    private String getCellDate(XSSFWorkbook excel, int row, int cell) {
        return excel.getSheetAt(0)
                .getRow(row)
                .getCell(cell)
                .toString();
    }

}
