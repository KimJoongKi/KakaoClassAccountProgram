package com.kcap.kakaoclassaccountprogram.web.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/test")
    @ResponseBody
    public String uploadTest() {
        return "test";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] files) {
        // TODO: 2022/02/06 file upload data insert

        // TODO: 2022/02/06 excel data insert (path)
        // TODO: 2022/02/06 excel etc data insert
        return "redirect:/";
    }
}
