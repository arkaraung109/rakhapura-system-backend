package com.pearlyadana.rakhapuraapp.util;

import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
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
public class StudentExcelGenerator {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<StudentDto> studentDtoList;

    public StudentExcelGenerator(List<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Students");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);

        createCell(row, 0, "စာရင်းသွင်းသည့်နေ့", style);
        createCell(row, 1, "အမည်", style);
        createCell(row, 2, "မွေးသက္ကရာဇ်", style);
        createCell(row, 3, "ကျား/မ", style);
        createCell(row, 4, "လူမျိုး", style);
        createCell(row, 5, "သာသနာရေးမှတ်ပုံတင်နံပါတ်", style);
        createCell(row, 6, "အဖအမည်", style);
        createCell(row, 7, "အမိအမည်", style);
        createCell(row, 8, "နေရပ်လိပ်စာ", style);
        createCell(row, 9, "ပြည်နယ်/တိုင်း", style);
        createCell(row, 10, "ကျောင်းတိုက်", style);
        createCell(row, 11, "ကျောင်းထိုင်ဆရာတော်", style);
        createCell(row, 12, "ကျောင်းတိုက်တည်နေရာ(မြို့နယ်)", style);
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

        for (StudentDto studentDto : this.studentDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, studentDto.getRegDate(), style);
            createCell(row, columnCount++, studentDto.getName(), style);
            createCell(row, columnCount++, studentDto.getDob(), style);
            createCell(row, columnCount++, studentDto.getSex(), style);
            createCell(row, columnCount++, studentDto.getNationality(), style);
            createCell(row, columnCount++, studentDto.getNrc(), style);
            createCell(row, columnCount++, studentDto.getFatherName(), style);
            createCell(row, columnCount++, studentDto.getMotherName(), style);
            createCell(row, columnCount++, studentDto.getAddress(), style);
            createCell(row, columnCount++, studentDto.getRegion().getName(), style);
            createCell(row, columnCount++, studentDto.getMonasteryName(), style);
            createCell(row, columnCount++, studentDto.getMonasteryHeadmaster(), style);
            createCell(row, columnCount++, studentDto.getMonasteryTownship(), style);
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
