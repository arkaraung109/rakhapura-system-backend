package com.pearlyadana.rakhapuraapp.util;

import com.pearlyadana.rakhapuraapp.model.response.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
public class StudentExamExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private CustomPaginationResponse<ResultResponse> customPaginationResponse;

    public StudentExamExcelGenerator(CustomPaginationResponse<ResultResponse> customPaginationResponse) {
        this.customPaginationResponse = customPaginationResponse;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Student Exam Marks");
        Row row0 = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        Row row5 = sheet.createRow(5);
        Row row6 = sheet.createRow(6);
        Row row7 = sheet.createRow(7);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setBold(true);
        style.setFont(font);

        sheet.addMergedRegion(new CellRangeAddress(5, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(5, 7, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(5, 7, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(5, 7, 3, 3));
        createCell(row0, 0, "စာသင်နှစ်", style);
        createCell(row1, 0, "စာမေးပွဲခေါင်းစဉ်", style);
        createCell(row2, 0, "အတန်း", style);
        createCell(row5, 0, "စဉ်", style);
        createCell(row5, 1, "ခုံနံပါတ်", style);
        createCell(row5, 2, "အမည်", style);
        createCell(row5, 3, "အဖအမည်", style);

        List<CustomExam> customExamList = this.customPaginationResponse.getTableHeader().getCustomExamList();
        int firstCol = 4;
        int lastCol = 0;
        for(int i = 0; i < customExamList.size(); i++) {
            int examSubjectCount = customExamList.get(i).getExamSubjectCount() + 1;
            if(examSubjectCount > 1) {
                lastCol = firstCol + customExamList.get(i).getExamSubjectCount();
                sheet.addMergedRegion(new CellRangeAddress(5, 5, firstCol, lastCol));
            } else {
                lastCol = firstCol;
            }
            createCell(row5, firstCol, customExamList.get(i).getExam().getSubjectType().getName(), style);
            firstCol = lastCol + 1;
        }
        sheet.addMergedRegion(new CellRangeAddress(5, 6, firstCol, firstCol));
        createCell(row5, firstCol, "ဘာသာရပ်အားလုံးပေါင်း", style);
        sheet.addMergedRegion(new CellRangeAddress(5, 7, firstCol + 1, firstCol + 1));
        createCell(row5, firstCol + 1, "Status", style);

        List<String> examSubjectList = this.customPaginationResponse.getTableHeader().getExamSubjectList();
        for(int i = 0; i < examSubjectList.size(); i++) {
            createCell(row6, i + 4, examSubjectList.get(i), style);
        }

        List<String> givenMarkList = this.customPaginationResponse.getTableHeader().getGivenMarkList();
        for(int i = 0; i < givenMarkList.size(); i++) {
            createCell(row7, i + 4, givenMarkList.get(i), style);
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
        int rowCount = 8;
        int seqNo = 1;
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for(ResultResponse resultResponse : this.customPaginationResponse.getElements()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, seqNo++, style);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getRegNo(), style);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getStudent().getName(), style);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getStudent().getFatherName(), style);

            for(ExamResult examResult : resultResponse.getExamResultList()) {
                for(SubjectResult subjectResult : examResult.getSubjectResultList()) {
                    createCell(row, columnCount++, subjectResult.getMark(),style);
                }
                createCell(row, columnCount++, examResult.getMark(), style);
            }

            createCell(row, columnCount++, resultResponse.getOverAllMark().getMark(), style);
            createCell(row, columnCount++, resultResponse.getStatus(), style);

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
