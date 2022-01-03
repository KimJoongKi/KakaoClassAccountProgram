package com.kcap.kakaoclassaccountprogram.testfile;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelTest {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/test/테스트파일.xlsx");  // 테스트 파일경로
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook excel = new XSSFWorkbook(fis);
        XSSFSheet sheet = excel.getSheet("카카오뱅크 거래내역");
        System.out.println(sheet.getSheetName());
        Row row = sheet.getRow(0);
        System.out.println(row.getCell(0));
        System.out.println(row.getCell(1));

        System.out.println();

    }

}
