package com.example.excel;

import com.example.exception.ApiException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExcelFileService {

    public ByteArrayInputStream createExcelSample() {
        List<String> headers = new ArrayList<>();
        headers.add("NO");
        headers.add("Name");
        headers.add("Age");
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("sheet1");

            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setBorderTop(BorderStyle.THIN);
            headStyle.setBorderBottom(BorderStyle.THIN);
            headStyle.setBorderLeft(BorderStyle.THIN);
            headStyle.setBorderRight(BorderStyle.THIN);
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headStyle.setAlignment(HorizontalAlignment.CENTER);

            // 헤더 생성
            Row row = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headStyle);
                cell.setCellValue(headers.get(i));
            }

            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue("1");
            dataRow.createCell(1).setCellValue("이진석");
            dataRow.createCell(2).setCellValue(34);

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("ExcelFileService - createExcelSample: {}", e.getMessage(), e);
            throw new ApiException("엑셀파일 생성 실패", HttpStatus.BAD_REQUEST);
        }
    }
}
