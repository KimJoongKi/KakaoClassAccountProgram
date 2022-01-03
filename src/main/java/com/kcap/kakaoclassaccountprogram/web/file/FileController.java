package com.kcap.kakaoclassaccountprogram.web.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/upload")
    @ResponseBody
    public String upload() {
        return "test";
    }
}
