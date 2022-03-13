package com.example.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExcelFileController {

    private final ExcelFileService excelFileService;

    @GetMapping("/api/v1/excel/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        ByteArrayInputStream excelSample = excelFileService.createExcelSample();
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(excelSample.readAllBytes());
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sample.xlsx");
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

}
