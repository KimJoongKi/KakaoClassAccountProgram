package com.kcap.kakaoclassaccountprogram.web.file;

import com.kcap.kakaoclassaccountprogram.web.file.form.FileForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/test")
    @ResponseBody
    public String uploadTest() {
        return "test";
    }

    @GetMapping("/upload")
    public String updateView(@ModelAttribute FileForm form) {
        return "file/upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@ModelAttribute FileForm form) {
        // TODO: 2022/02/06 file upload data insert 와 진짜 한글이 이렇게 빨라지다...
        // TODO: 2022/02/06 excel data insert (path)
        // TODO: 2022/02/06 excel etc data insert
        return "file/upload";
    }
}
