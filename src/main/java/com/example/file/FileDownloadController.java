package com.example.file;

import java.net.MalformedURLException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    @GetMapping("/api/v1/file/download/url")
    public void downloadFormUrl(@RequestParam String downloadUrl, HttpServletResponse servletResponse) throws MalformedURLException {
        fileDownloadService.downloadFromUrl(downloadUrl, servletResponse);
    }

}