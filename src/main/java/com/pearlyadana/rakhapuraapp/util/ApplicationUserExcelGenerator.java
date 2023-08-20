package com.pearlyadana.rakhapuraapp.util;

import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
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
public class ApplicationUserExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ApplicationUserDto> applicationUserDtoList;

    public ApplicationUserExcelGenerator(List<ApplicationUserDto> applicationUserDtoList) {
        this.applicationUserDtoList = applicationUserDtoList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Application Users");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setBold(true);
        style.setFont(font);

        createCell(row, 0, "Index", style);
        createCell(row, 1, "First Name", style);
        createCell(row, 2, "Last Name", style);
        createCell(row, 3, "Username", style);
        createCell(row, 4, "Account Status", style);
        createCell(row, 5, "Role", style);
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

        for (ApplicationUserDto applicationUserDto : this.applicationUserDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, seqNo++, style);
            createCell(row, columnCount++, applicationUserDto.getFirstName(), style);
            createCell(row, columnCount++, applicationUserDto.getLastName(), style);
            createCell(row, columnCount++, applicationUserDto.getLoginUserName(), style);
            createCell(row, columnCount++, applicationUserDto.isActiveStatus() ? "active" : "disabled", style);
            createCell(row, columnCount++, applicationUserDto.getRole().getName(), style);
        }
        for(int i = 0 ; i < 6; i++) {
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
