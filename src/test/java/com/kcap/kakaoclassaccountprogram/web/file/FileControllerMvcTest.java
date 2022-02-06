package com.kcap.kakaoclassaccountprogram.web.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class FileControllerMvcTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    public void upload_화면() throws Exception {
        mockMvc.perform(get("/file/upload"))
                .andExpect(status().isOk());
    }
}
