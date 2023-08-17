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
        Row row3 = sheet.createRow(3);
        Row row4 = sheet.createRow(4);
        Row row5 = sheet.createRow(5);
        Row row6 = sheet.createRow(6);
        Row row9 = sheet.createRow(9);
        Row row10 = sheet.createRow(10);
        Row row11 = sheet.createRow(11);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setBold(true);
        style.setFont(font);

        sheet.addMergedRegion(new CellRangeAddress(9, 11, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(9, 11, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(9, 11, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(9, 11, 3, 3));
        createCell(row0, 0, "စာသင်နှစ်", style);
        createCell(row1, 0, "စာမေးပွဲခေါင်းစဉ်", style);
        createCell(row2, 0, "အတန်း", style);
        createCell(row3, 0, "ဖြေဆိုသူစုစုပေါင်း", style);
        createCell(row4, 0, "အောင်မြင်သူစုစုပေါင်း", style);
        createCell(row5, 0, "ကျရှုံးသူစုစုပေါင်း", style);
        createCell(row6, 0, "ကုစားစုစုပေါင်း", style);
        createCell(row9, 0, "စဉ်", style);
        createCell(row9, 1, "ခုံနံပါတ်", style);
        createCell(row9, 2, "အမည်", style);
        createCell(row9, 3, "အဖအမည်", style);

        List<CustomExam> customExamList = this.customPaginationResponse.getTableHeader().getCustomExamList();
        int firstCol = 4;
        int lastCol = 0;
        for (CustomExam customExam : customExamList) {
            int examSubjectCount = customExam.getExamSubjectCount() + 1;
            if (examSubjectCount > 1) {
                lastCol = firstCol + customExam.getExamSubjectCount();
                sheet.addMergedRegion(new CellRangeAddress(9, 9, firstCol, lastCol));
            } else {
                lastCol = firstCol;
            }
            createCell(row9, firstCol, customExam.getExam().getSubjectType().getName(), style);
            firstCol = lastCol + 1;
        }
        sheet.addMergedRegion(new CellRangeAddress(9, 10, firstCol, firstCol));
        createCell(row9, firstCol, "ဘာသာရပ်ကြီးအားလုံးပေါင်း", style);
        sheet.addMergedRegion(new CellRangeAddress(9, 11, firstCol + 1, firstCol + 1));
        createCell(row9, firstCol + 1, "အောင်/ရှုံး/ကုစား", style);

        List<String> examSubjectList = this.customPaginationResponse.getTableHeader().getExamSubjectList();
        for(int i = 0; i < examSubjectList.size(); i++) {
            createCell(row10, i + 4, examSubjectList.get(i), style);
        }

        List<String> givenMarkList = this.customPaginationResponse.getTableHeader().getGivenMarkList();
        for(int i = 0; i < givenMarkList.size(); i++) {
            createCell(row11, i + 4, givenMarkList.get(i), style);
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
        createCell(row3, 1, this.customPaginationResponse.getTotalAnswered(), normalStyle);
        createCell(row4, 1, this.customPaginationResponse.getTotalPassed(), normalStyle);
        createCell(row5, 1, this.customPaginationResponse.getTotalFailed(), normalStyle);
        createCell(row6, 1, this.customPaginationResponse.getTotalModerated(), normalStyle);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount, true);
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
        int rowCount = 12;
        int seqNo = 1;

        CellStyle redStyle = workbook.createCellStyle();
        redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        redStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont redFont = workbook.createFont();
        redFont.setFontHeight(11);
        redFont.setBold(true);
        redFont.setColor(IndexedColors.RED.index);
        redStyle.setFont(redFont);

        CellStyle greenStyle = workbook.createCellStyle();
        greenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        greenStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont greenFont = workbook.createFont();
        greenFont.setFontHeight(11);
        greenFont.setBold(true);
        greenFont.setColor(IndexedColors.GREEN.index);
        greenStyle.setFont(greenFont);

        CellStyle orangeStyle = workbook.createCellStyle();
        orangeStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        orangeStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont orangeFont = workbook.createFont();
        orangeFont.setFontHeight(11);
        orangeFont.setBold(true);
        orangeFont.setColor(IndexedColors.LIGHT_ORANGE.index);
        orangeStyle.setFont(orangeFont);

        CellStyle defaultStyle = workbook.createCellStyle();
        defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        defaultStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont defaultFont = workbook.createFont();
        defaultFont.setFontHeight(11);
        defaultFont.setBold(true);
        defaultStyle.setFont(defaultFont);

        for(ResultResponse resultResponse : this.customPaginationResponse.getElements()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, seqNo++, defaultStyle);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getRegNo(), defaultStyle);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getStudent().getName(), defaultStyle);
            createCell(row, columnCount++, resultResponse.getAttendance().getStudentClass().getStudent().getFatherName(), defaultStyle);

            for(ExamResult examResult : resultResponse.getExamResultList()) {
                for(SubjectResult subjectResult : examResult.getSubjectResultList()) {
                    if(subjectResult.getStatus().equalsIgnoreCase("fail")) {
                        createCell(row, columnCount++, subjectResult.getMark(), redStyle);
                    } else if(subjectResult.getStatus().equalsIgnoreCase("pass")) {
                        createCell(row, columnCount++, subjectResult.getMark(), greenStyle);
                    } else if(subjectResult.getStatus().equalsIgnoreCase("moderation")) {
                        createCell(row, columnCount++, subjectResult.getMark(), orangeStyle);
                    } else {
                        createCell(row, columnCount++, subjectResult.getMark(), defaultStyle);
                    }
                }

                if(examResult.getStatus().equalsIgnoreCase("fail")) {
                    createCell(row, columnCount++, examResult.getMark(), redStyle);
                } else if(examResult.getStatus().equalsIgnoreCase("pass")) {
                    createCell(row, columnCount++, examResult.getMark(), greenStyle);
                } else {
                    createCell(row, columnCount++, examResult.getMark(), defaultStyle);
                }
            }

            if(resultResponse.getOverAllMark().getStatus().equalsIgnoreCase("fail")) {
                createCell(row, columnCount++, resultResponse.getOverAllMark().getMark(), redStyle);
            } else {
                createCell(row, columnCount++, resultResponse.getOverAllMark().getMark(), greenStyle);
            }

            if(resultResponse.getStatus().equalsIgnoreCase("fail")) {
                createCell(row, columnCount++, "ရှုံး", redStyle);
            } else if(resultResponse.getStatus().equalsIgnoreCase("pass")) {
                createCell(row, columnCount++, "အောင်", greenStyle);
            } else {
                createCell(row, columnCount++, "ကုစား", orangeStyle);
            }
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
