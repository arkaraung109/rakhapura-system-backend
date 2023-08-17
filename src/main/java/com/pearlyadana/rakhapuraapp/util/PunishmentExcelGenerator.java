package com.pearlyadana.rakhapuraapp.util;

import com.pearlyadana.rakhapuraapp.model.request.PunishmentDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@ApplicationScope
public class PunishmentExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<PunishmentDto> punishmentDtoList;

    public PunishmentExcelGenerator(List<PunishmentDto> punishmentDtoList) {
        this.punishmentDtoList = punishmentDtoList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Punished Students");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setBold(true);
        style.setFont(font);

        createCell(row, 0, "စဉ်", style);
        createCell(row, 1, "အမည်", style);
        createCell(row, 2, "အဖအမည်", style);
        createCell(row, 3, "ကျူးလွန်သည့်ပြစ်ဒဏ်", style);
        createCell(row, 4, "အကြောင်းအရာ", style);
        createCell(row, 5, "ကျူးလွန်သည့်နေ့စွဲ", style);
        createCell(row, 6, "ပြစ်ဒဏ်ပေးစတင်သည့်နေ့စွဲ", style);
        createCell(row, 7, "ပြစ်ဒဏ်ပေးပြီးဆုံးသည့်နေ့စွဲ", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        int seqNo = 1;
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (PunishmentDto punishmentDto : this.punishmentDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, seqNo++, style);
            createCell(row, columnCount++, punishmentDto.getStudent().getName(), style);
            createCell(row, columnCount++, punishmentDto.getStudent().getFatherName(), style);
            createCell(row, columnCount++, punishmentDto.getPunishment(), style);
            createCell(row, columnCount++, punishmentDto.getDescription(), style);
            createCell(row, columnCount++, punishmentDto.getEventDate(), style);
            createCell(row, columnCount++, punishmentDto.getStartDate(), style);
            createCell(row, columnCount++, punishmentDto.getEndDate(), style);
        }
        for(int i = 0 ; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
