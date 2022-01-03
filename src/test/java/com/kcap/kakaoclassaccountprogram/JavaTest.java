package com.kcap.kakaoclassaccountprogram;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JavaTest {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/test");
        List<MockMultipartFile> fileList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File file1 : files) {
            MockMultipartFile mockMultipartFile =
                    new MockMultipartFile(file1.getName(), new FileInputStream(file1));
            fileList.add(mockMultipartFile);
        }
        for (MockMultipartFile mockMultipartFile : fileList) {
            System.out.println("mockMultipartFile.getOriginalFilename() = " + mockMultipartFile.getName());
            System.out.println("mockMultipartFile.getOriginalFilename() = " + mockMultipartFile.getSize());
        }
    }
}
