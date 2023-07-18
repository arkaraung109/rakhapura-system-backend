package com.pearlyadana.rakhapuraapp.util;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
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
public class StudentHostelExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<StudentClassDto> studentClassDtoList;

    public StudentHostelExcelGenerator(List<StudentClassDto> studentClassDtoList) {
        this.studentClassDtoList = studentClassDtoList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Assigned Students In Hostels");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);

        createCell(row, 0, "ခုံနံပါတ်", style);
        createCell(row, 1, "အမည်", style);
        createCell(row, 2, "အဖအမည်", style);
        createCell(row, 3, "စာသင်နှစ်", style);
        createCell(row, 4, "စာမေးပွဲခေါင်းစဉ်", style);
        createCell(row, 5, "အတန်း", style);
        createCell(row, 6, "တည်းခိုဆော", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
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
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (StudentClassDto studentClassDto : this.studentClassDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, studentClassDto.getRegNo(), style);
            createCell(row, columnCount++, studentClassDto.getStudent().getName(), style);
            createCell(row, columnCount++, studentClassDto.getStudent().getFatherName(), style);
            createCell(row, columnCount++, studentClassDto.getStudentClass().getAcademicYear().getName(), style);
            createCell(row, columnCount++, studentClassDto.getExamTitle().getName(), style);
            createCell(row, columnCount++, studentClassDto.getStudentClass().getGrade().getName(), style);
            createCell(row, columnCount++, studentClassDto.getHostel().getName(), style);
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
