package com.example.file;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Value("${spring.servlet.multipart.location}")
    private String FILE_UPLOAD_DIRECTORY;

    @PostMapping("/api/v1/file-upload")
    public void fileUpload(@RequestParam MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return;
        }

        multipartFile.transferTo(new File( FILE_UPLOAD_DIRECTORY + "/", multipartFile.getOriginalFilename()));
    }
}