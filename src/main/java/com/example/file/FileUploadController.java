package com.example.file;

import com.example.exception.ApiException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class FileUploadController {

    @Value("${file.upload.location}")
    private String FILE_UPLOAD_DIRECTORY;


    @PostMapping("/api/v1/file/upload")
    public void fileUpload(@RequestParam MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            throw new ApiException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new ApiException("파일의 이름이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        multipartFile.transferTo(new File(FILE_UPLOAD_DIRECTORY + "/", multipartFile.getOriginalFilename()));
    }


}