package com.kcap.kakaoclassaccountprogram;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JavaTest {

    @Test
    public void test() throws IOException {

        String fileName = "테스트파일";
        String contentType = "xlsx";
        String filePath = "src/main/resources/test/테스트파일.xlsx";

        FileInputStream fileInputStream =
                new FileInputStream(new File(filePath));
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(fileName,
                        fileName + "." + contentType,
                        contentType,
                        fileInputStream);

        assertThat(mockMultipartFile.getOriginalFilename(), is(fileName + "."+ contentType));
    }
}
