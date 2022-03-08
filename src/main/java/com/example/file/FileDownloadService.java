package com.example.file;

import com.example.exception.ApiException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileDownloadService {


    @Value("${file.download.location}")
    private String FILE_DOWNLOAD_DIRECTORY;

    private void downloadFromPath(String path, HttpServletResponse response) {
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기

            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
            OutputStream out = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int read;
            while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
                out.write(buffer, 0, read);
            }
        } catch (Exception e) {
            log.error("download - {}", e.getMessage(), e);
            throw new ApiException("파일 다운로드 실패", HttpStatus.BAD_REQUEST);
        }
    }

    public void downloadFromUrl(String downloadUrl, HttpServletResponse response) throws MalformedURLException {
        URL url = new URL(downloadUrl);
        String fileName = FilenameUtils.getName(url.getPath());
        if (fileName.isEmpty()) {
            throw new ApiException("파일 다운로드 실패", HttpStatus.BAD_REQUEST);
        }
        String filePath = FILE_DOWNLOAD_DIRECTORY + "/" + fileName;
        try (BufferedInputStream in = new BufferedInputStream(url.openStream()); FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            downloadFromPath(filePath, response);
        } catch (IOException e) {
            throw new ApiException("파일 다운로드 실패", HttpStatus.BAD_REQUEST);
        }
    }

}