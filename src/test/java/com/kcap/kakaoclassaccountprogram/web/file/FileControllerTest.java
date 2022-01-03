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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

}
