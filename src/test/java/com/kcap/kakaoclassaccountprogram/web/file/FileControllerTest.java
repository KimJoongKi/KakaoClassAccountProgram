package com.kcap.kakaoclassaccountprogram.web.file;

import com.kcap.kakaoclassaccountprogram.KakaoClassAccountProgramApplication;
import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


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

import static org.junit.jupiter.api.Assertions.*;

/**
 * 테스트파일 데이터는 본인 기준에 맞게 변경 후 진행
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class FileControllerTest {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

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
        XSSFWorkbook excel = new XSSFWorkbook(mockMultipartFile.getInputStream());
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

        Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Pattern numberPattern = Pattern.compile("[0-9]+");

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
                if (!failFlag) {
                    successRow.add(i);
                }

            }
            assertEquals(successRow.size(), 45, 43);

        }
    }

    // TODO: 2022/01/16 excel date insert, select,
    @Test
    public void excel_date_insert_select() throws IOException {
        File file = new File("src/main/resources/test");  // 테스트 파일경로
        File[] files = file.listFiles();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", new FileInputStream(files[0]));
        XSSFWorkbook excel = new XSSFWorkbook(mockMultipartFile.getInputStream());
        XSSFSheet sheet = excel.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();

        List<TransactionHistory> successRow = new ArrayList<>();

        Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Pattern numberPattern = Pattern.compile("[0-9]+");

        for (int i = 11; i <= lastRowNum; i++) {
            Boolean failFlag = false;
            XSSFRow row = sheet.getRow(i);
            int lastCellNum = (int) row.getLastCellNum();
            Matcher matcher = null;
            TransactionHistoryVO vo = new TransactionHistoryVO();
            for (int j = 1; j <= lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                switch (j) {
                    case 1:
                        matcher = datePattern.matcher(cell.getStringCellValue());
                        if (!matcher.find()) {
                            failFlag = true;
                            break;
                        }
                        vo.setDate(LocalDateTime.parse(matcher.group(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
                        break;
                    case 2:
                        vo.setDivision(cell.getStringCellValue());
                        break;
                    case 3:
                        matcher = numberPattern.matcher(cell.getStringCellValue().replaceAll("[^0-9]", ""));
                        if (!matcher.find()) {
                            failFlag = true;
                            break;
                        }
                        vo.setPrice(Integer.parseInt(matcher.group()));
                        break;
                    case 4:
                        matcher = numberPattern.matcher(cell.getStringCellValue().replaceAll("[^0-9]", ""));
                        if (!matcher.find()) {
                            failFlag = true;
                            break;
                        }
                        vo.setAfterBalance(Integer.parseInt(matcher.group()));
                        break;
                    case 6:
                        vo.setContents(cell.getStringCellValue());
                        break;
                    case 7:
                        vo.setMemo(cell.getStringCellValue());
                        break;
                }
            }
            if (!failFlag) {
                successRow.add(
                        TransactionHistory.builder()
                                .date(vo.getDate())
                                .division(vo.getDivision())
                                .price(vo.getPrice())
                                .afterBalance(vo.getAfterBalance())
                                .contents(vo.getContents())
                                .memo(vo.getMemo())
                                .build());
            }
        }
        transactionHistoryRepository.saveAll(successRow);
        assertEquals(transactionHistoryRepository.findAll().size(),43);

    }




    // TODO: 2022/01/16 미납내역을 이미지 or pdf 파일로 변환하여 내려받는다.

    private String getCellData(XSSFWorkbook excel, int row, int cell) {
        return excel.getSheetAt(0)
                .getRow(row)
                .getCell(cell)
                .toString();
    }

}


