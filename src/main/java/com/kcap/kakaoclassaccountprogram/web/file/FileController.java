package com.kcap.kakaoclassaccountprogram.web.file;

import com.kcap.kakaoclassaccountprogram.web.file.form.FileForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    MessageSource messageSource;

    @RequestMapping("/test")
    @ResponseBody
    public String uploadTest() {
        return "test";
    }

    @GetMapping("/upload")
    public String updateView(@ModelAttribute("file") FileForm form) {
        return "file/upload";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute("file") FileForm form, BindingResult bindingResult) {

        // TODO: 2022/02/22 확장자가 다를 때 반환하는 부분 작성
        for (MultipartFile uploadFile : form.getUploadFiles()) {
            String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
            if (!messageSource.getMessage("xlsx", null, null).equals(extension)) {
                bindingResult.reject("fileExtention", null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "file/upload";
        }
        // TODO: 2022/02/22 확장자 통과 후 엑셀의 형식이 다를 때 반환하는 부분 작성 
        // TODO: 2022/02/06 file upload data insert
        // TODO: 2022/02/06 excel data insert (path)
        return "file/upload";
    }
}
