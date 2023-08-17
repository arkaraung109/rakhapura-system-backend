package com.pearlyadana.rakhapuraapp.util;

import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomExam;
import com.pearlyadana.rakhapuraapp.model.response.CustomPaginationResponse;
import com.pearlyadana.rakhapuraapp.model.response.ResultResponse;
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
public class AttendanceExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private CustomPaginationResponse<ResultResponse> customPaginationResponse;

    public AttendanceExcelGenerator(CustomPaginationResponse<ResultResponse> customPaginationResponse) {
        this.customPaginationResponse = customPaginationResponse;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Attended Students To Exams");
        Row row0 = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        Row row5 = sheet.createRow(5);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setBold(true);
        style.setFont(font);

        createCell(row0, 0, "စာသင်နှစ်", style);
        createCell(row1, 0, "စာမေးပွဲခေါင်းစဉ်", style);
        createCell(row2, 0, "အတန်း", style);
        createCell(row5, 0, "စဉ်", style);
        createCell(row5, 1, "ခုံနံပါတ်", style);
        createCell(row5, 2, "အမည်", style);
        createCell(row5, 3, "အဖအမည်", style);

        List<CustomExam> customExamList = this.customPaginationResponse.getTableHeader().getCustomExamList();
        for(int i = 0; i < customExamList.size(); i++) {
            createCell(row5, i + 4, customExamList.get(i).getExam().getSubjectType().getName(), style);
        }

        CellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        normalStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont normalFont = workbook.createFont();
        normalFont.setFontHeight(11);
        normalStyle.setFont(normalFont);
        createCell(row0, 1, this.customPaginationResponse.getTableHeader().getAcademicYear(), normalStyle);
        createCell(row1, 1, this.customPaginationResponse.getTableHeader().getExamTitle(), normalStyle);
        createCell(row2, 1, this.customPaginationResponse.getTableHeader().getGrade(), normalStyle);
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
        int rowCount = 6;
        int seqNo = 1;
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (ResultResponse resultResponse : this.customPaginationResponse.getElements()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, seqNo++, style);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getRegNo(), style);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getStudent().getName(), style);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getStudent().getFatherName(), style);

            for(AttendanceDto attendanceDto : resultResponse.getAttendedExamList()) {
                if(attendanceDto.isPresent()) {
                    createCell(row, columnCount++, String.valueOf('\u2714'), style);
                } else {
                    createCell(row, columnCount++, String.valueOf('\u2718'), style);
                }
            }
        }
        for(int i = 0 ; i < this.customPaginationResponse.getElements().size() + 4; i++) {
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
