package com.kcap.kakaoclassaccountprogram.web.file;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

/**
 * 테스트파일 데이터는 본인 기준에 맞게 변경 후 진행
 */
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
            queue.offer(new MockMultipartFile(file1.getName(), new FileInputStream(file1)));
        }

        // 테스트 파일 명에 맞게 변경
        assertEquals("테스트파일.xlsx", queue.poll().getName());
        assertEquals("테스트파일2.xlsx", queue.poll().getName());

    }

    @Test
    public void 카카오뱅크_엑셀파일_검증_테스트() throws IOException {
        File file = new File("src/main/resources/test");  // 테스트 파일경로
        File[] files = file.listFiles();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", new FileInputStream(files[0]));
        XSSFWorkbook excel = new XSSFWorkbook(mockMultipartFile.getInputStream());;
        String sheetName = excel.getSheetName(0);
        String cellName = getCellData(excel, 0, 1);
        String accountHolder = getCellData(excel, 3, 2);
        String accountNumber = getCellData(excel, 4, 2);
        Pattern pattern = Pattern.compile("([0-9])+");
        Matcher matcher = pattern.matcher(accountNumber);
        matcher.find();

        assertEquals("카카오뱅크 거래내역", sheetName, cellName);
        assertEquals("김중기", accountHolder);
        assertEquals("1826", matcher.group());
    }

    @Test
    public void 셀_데이터_정규표현식확인() throws IOException {
        /**
         * 1열 데이트 정규식
         * 3,4열  정규식
         * todo: 패턴은 계속 만들면 자원낭비가 심하다 static으로 만들어 두었다가 사용해야한다.
         */
        File file = new File("src/main/resources/test");  // 테스트 파일경로
        File[] files = file.listFiles();
        List<MockMultipartFile> mockMultipartFiles = new ArrayList<MockMultipartFile>();

        for (File f : files) {
            mockMultipartFiles.add(new MockMultipartFile(f.getName(), new FileInputStream(f)));
        }

        for (MockMultipartFile mockMultipartFile : mockMultipartFiles) {
            XSSFWorkbook excel = new XSSFWorkbook(mockMultipartFile.getInputStream());
            XSSFSheet sheet = excel.getSheetAt(0);
            XSSFRow row = sheet.getRow(10);
            int lastRowNum = sheet.getLastRowNum();
            int lastCellNum = (int) row.getLastCellNum();

            List<Integer> successRow = new ArrayList<>();
            List<Integer> failRow = new ArrayList<>();

            Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2} \\d{2}:\\d{2}:\\d{2}");
            Pattern numberPattern = Pattern.compile("[0-9]+");

            for (int i = 11; i <= lastRowNum; i++) {
                Boolean failFlag = false;
                XSSFRow selectRow = sheet.getRow(i);
                for (int j = 1; j <= lastCellNum; j++) {
                    XSSFCell cell = selectRow.getCell(j);
                    Matcher matcher = null;
                    switch (j) { // 셀
                        case 1:
                            matcher = datePattern.matcher(cell.getStringCellValue());
                            if (!matcher.find()) {
                                failFlag = true;
                                break;
                            }
                            break;
                        case 2: case 6: case 7:
                            break;
                        case 3: case 4:
                            matcher = numberPattern.matcher(cell.getStringCellValue().replaceAll("[^0-9]", ""));
                            if (!matcher.find()) {
                                failFlag = true;
                                break;
                            }
                            break;
                    }
                }
                if (failFlag) {
                    failRow.add(i);
                } else {
                    successRow.add(i);
                }

            }

            assertEquals(failRow.size(), 2);
            assertEquals(successRow.size(), 42);

        }
    }

    private String getCellData(XSSFWorkbook excel, int row, int cell) {
        return excel.getSheetAt(0)
                .getRow(row)
                .getCell(cell)
                .toString();
    }

}

