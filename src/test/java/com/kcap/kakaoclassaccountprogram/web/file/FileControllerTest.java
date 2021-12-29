package com.kcap.kakaoclassaccountprogram.web.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.io.File;
import java.io.FileInputStream;

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
    public void 테스트파일_업로드_테스트() throws Exception {
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

        assertEquals(fileName, mockMultipartFile.getName());
        assertThat(mockMultipartFile.getOriginalFilename(), is(fileName + "."+ contentType));
    }

}