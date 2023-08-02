package com.pearlyadana.rakhapuraapp.rest;

import com.google.myanmartools.Transliterate;
import com.google.myanmartools.TransliterateU2Z;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.request.PdfNameZipStreamMapping;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.AttendanceService;
import com.pearlyadana.rakhapuraapp.service.ExamService;
import com.pearlyadana.rakhapuraapp.service.StudentCardService;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(value = "/api/v1/student-cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentCardController {

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private StudentCardService studentCardService;

    @Autowired
    private AttendanceService attendanceService;

    private static final String cardExportLocation = "D:\\";

    private static final Transliterate zawgyiConverter = new TransliterateU2Z("Unicode to Zawgyi");

    @GetMapping("/segment/search")
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentCardService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<DataResponse> generate(@RequestParam List<UUID> idList, @RequestParam String cardDate, @RequestParam int examHoldingTimes) throws DocumentException, IOException {
        List<StudentClassDto> studentClassDtoList = new ArrayList<>();
        List<UUID> createdList = new ArrayList<>();
        List<UUID> errorList = new ArrayList<>();
        boolean hasAtLeastOneExam = false;
        for(UUID id : idList) {
            StudentClassDto studentClassDto = this.studentClassService.findById(id);
            studentClassDtoList.add(studentClassDto);
            List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(studentClassDto.getStudentClass().getAcademicYear().getId(), studentClassDto.getExamTitle().getId(), studentClassDto.getStudentClass().getGrade().getId());
            if(examDtoList.isEmpty()) {
                errorList.add(id);
            } else {
                hasAtLeastOneExam = true;
            }
        }
        if(hasAtLeastOneExam) {
            BaseFont zawgyiFont = BaseFont.createFont("src/main/resources/fonts/ZawgyiOne.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(zawgyiFont, 12, Font.NORMAL);
            Font titleFont = new Font(zawgyiFont, 14, Font.BOLD);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH.mm.ss");
            String zipName = "Student_Card_" + dateFormat.format(new Date());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream fos = new ZipOutputStream(new FileOutputStream(new File(cardExportLocation + zipName + ".zip")));
            ZipOutputStream zos = new ZipOutputStream(baos);
            List<PdfNameZipStreamMapping> map = new ArrayList<>();

            for(StudentClassDto studentClassDto : studentClassDtoList) {
                List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(studentClassDto.getStudentClass().getAcademicYear().getId(), studentClassDto.getExamTitle().getId(), studentClassDto.getStudentClass().getGrade().getId());
                if(!examDtoList.isEmpty()) {
                    int maxRegSeqNo = this.studentClassService.findMaxRegSeqNo(studentClassDto.getExamTitle().getId(), studentClassDto.getStudentClass().getAcademicYear().getId(), studentClassDto.getStudentClass().getGrade().getId());
                    String regSeq = studentClassDto.getStudentClass().getGrade().getAbbreviate() + "-" + this.getRegSeqNoInMyanmar(String.valueOf(maxRegSeqNo + 1));
                    studentClassDto.setRegSeqNo(maxRegSeqNo + 1);
                    studentClassDto.setRegNo(regSeq);
                    if(this.studentClassService.update(studentClassDto, studentClassDto.getId()) == null) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    createdList.add(studentClassDto.getId());

                    for(ExamDto examDto : examDtoList) {
                        AttendanceDto attendanceDto = new AttendanceDto();
                        attendanceDto.setPresent(false);
                        attendanceDto.setExam(examDto);
                        attendanceDto.setStudentClass(studentClassDto);
                        if(this.attendanceService.save(attendanceDto) == null) {
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }

                    PdfNameZipStreamMapping entry = new PdfNameZipStreamMapping();
                    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                    entry.setEntryName(studentClassDto.getRegNo());
                    entry.setStream(pdfStream);
                    map.add(entry);

                    Document doc = new Document(PageSize.A5.rotate(), 36, 36, 36, 36);
                    doc.setMargins((float) 0.5, (float) 0.5, (float) 0.5, (float) 0.5);
                    PdfWriter writer = PdfWriter.getInstance(doc, pdfStream);
                    doc.open();

                    //add image
                    Image img = null;
                    img = Image.getInstance("src/main/resources/imgs/favicon.png");
                    img.scaleAbsolute(150f, 130f);
                    img.setAlignment(Image.MIDDLE);
                    doc.add(img);

                    Paragraph dataParagraph = new Paragraph(
                            "("
                                    + examHoldingTimes
                                    + zawgyiConverter.convert(") အကြိမ်\nရက္ခပူရသာသနာလင်္ကာရစာမေးပွဲ\n")
                                    + zawgyiConverter.convert(studentClassDto.getStudentClass().getGrade().getName()) + "\n"
                                    + zawgyiConverter.convert("စာမေးပွဲဖြေဆိုခွင့်ကတ်ပြား"), titleFont
                    );
                    dataParagraph.setAlignment(Element.ALIGN_CENTER);
                    doc.add(dataParagraph);
                    //doc.add(new Paragraph("\n\n\n\n\n\n\n"));

                    Phrase phrase1 = new Phrase(zawgyiConverter.convert("နေ့စွဲ"),font);
                    PdfContentByte canvas1 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase1, 50, 50, 0);
                    phrase1 = new Phrase(cardDate);
                    canvas1 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase1, 150, 50, 0);

                    Phrase phrase2 = new Phrase(zawgyiConverter.convert("မြို့နယ်"), font);
                    PdfContentByte canvas2 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase2, 50, 80, 0);
                    phrase2 = new Phrase(zawgyiConverter.convert(studentClassDto.getStudent().getRegion().getName()), font);
                    canvas2 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase2, 150, 80, 0);

                    Phrase phrase3 = new Phrase(zawgyiConverter.convert("ကျောင်းတိုက်"), font);
                    PdfContentByte canvas3 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas3, Element.ALIGN_LEFT, phrase3, 50, 110, 0);
                    phrase3 = new Phrase(zawgyiConverter.convert(studentClassDto.getStudent().getMonasteryName()), font);
                    canvas3 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas3, Element.ALIGN_LEFT, phrase3, 150, 110, 0);

                    Phrase phrase4 = new Phrase(zawgyiConverter.convert("ဘွဲ့တော်"), font);
                    PdfContentByte canvas4 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, phrase4, 50, 140, 0);
                    phrase4 = new Phrase(zawgyiConverter.convert(studentClassDto.getStudent().getName()), font);
                    canvas4 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, phrase4, 150, 140, 0);

                    Phrase phrase5 = new Phrase(zawgyiConverter.convert("ခုံနံပါတ်"), font);
                    PdfContentByte canvas5 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, phrase5, 50, 170, 0);
                    phrase5 = new Phrase(zawgyiConverter.convert(studentClassDto.getRegNo()),font);
                    canvas5 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, phrase5, 150, 170, 0);

                    Phrase signPhrase = new Phrase(zawgyiConverter.convert("စာဖြေသူလက်မှတ်"), font);
                    canvas5 = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, signPhrase, 450, 50, 0);

                    doc.close();
                    writer.close();
                    pdfStream.close();
                }
            }

            for(PdfNameZipStreamMapping item : map) {
                byte[] bytes = new byte[item.getStream().size()];
                ByteArrayInputStream fis = new ByteArrayInputStream(item.getStream().toByteArray());
                BufferedInputStream bis = new BufferedInputStream(fis);
                zos.putNextEntry(new ZipEntry(item.getEntryName() + ".pdf"));
                fos.putNextEntry(new ZipEntry(item.getEntryName() + ".pdf"));

                int bytesRead;
                while((bytesRead = bis.read(bytes)) != -1) {
                    zos.write(bytes, 0, bytesRead);
                    fos.write(bytes, 0, bytesRead);
                }
                bis.close();
                fis.close();
            }
            zos.closeEntry();
            fos.closeEntry();
            baos.flush();
            zos.close();
            fos.close();
            baos.close();
        }

        if(!createdList.isEmpty() && errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CREATED.value(), createdList.size(), 0);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        if(!errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.NOT_FOUND.value(), createdList.size(), errorList.size());
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getRegSeqNoInMyanmar(String numberString) {
        StringBuilder sb = new StringBuilder();
        for(Character chr : numberString.toCharArray()) {
            switch (chr) {
                case '0': sb.append('၀'); break;
                case '1': sb.append('၁'); break;
                case '2': sb.append('၂'); break;
                case '3': sb.append('၃'); break;
                case '4': sb.append('၄'); break;
                case '5': sb.append('၅'); break;
                case '6': sb.append('၆'); break;
                case '7': sb.append('၇'); break;
                case '8': sb.append('၈'); break;
                case '9': sb.append('၉'); break;
            }
        }
        return sb.toString();
    }

}
