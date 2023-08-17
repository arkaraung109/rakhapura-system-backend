package com.pearlyadana.rakhapuraapp.service;

import com.google.myanmartools.Transliterate;
import com.google.myanmartools.TransliterateU2Z;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.pearlyadana.rakhapuraapp.entity.StudentClass;
import com.pearlyadana.rakhapuraapp.mapper.AttendanceMapper;
import com.pearlyadana.rakhapuraapp.mapper.StudentClassMapper;
import com.pearlyadana.rakhapuraapp.model.request.*;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.AttendanceRepository;
import com.pearlyadana.rakhapuraapp.repository.StudentClassRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class StudentCardServiceImpl implements StudentCardService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentClassMapper studentClassMapper = Mappers.getMapper(StudentClassMapper.class);

    private final AttendanceMapper attendanceMapper = Mappers.getMapper(AttendanceMapper.class);

    private static final Transliterate zawgyiConverter = new TransliterateU2Z("Unicode to Zawgyi");

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId) {
        Pageable sortedByCreatedTimestamp;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = null;
        if(examTitleId == 0 && academicYearId == 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllForStudentCard(sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByExamTitleForStudentCard(examTitleId, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByAcademicYearForStudentCard(academicYearId, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByGradeForStudentCard(gradeId, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearForStudentCard(examTitleId, academicYearId, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeForStudentCard(examTitleId, gradeId, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeForStudentCard(academicYearId, gradeId, sortedByCreatedTimestamp);
        } else {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeForStudentCard(examTitleId, academicYearId, gradeId, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.studentClassMapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Override
    public void generate(StudentCard studentCard, List<StudentClassDto> studentClassDtoList, List<ExamDto> examDtoList, HttpServletResponse response) throws DocumentException, IOException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            studentCard.setCardDate(outputDateFormat.format(inputDateFormat.parse(studentCard.getCardDate())));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }

        BaseFont zawgyiFont = BaseFont.createFont("src/main/resources/fonts/ZawgyiOne.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(zawgyiFont, 12, Font.NORMAL);
        Font titleFont = new Font(zawgyiFont, 14, Font.BOLD);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        List<PdfNameZipStreamMapping> map = new ArrayList<>();

        for(StudentClassDto studentClassDto : studentClassDtoList) {
            int maxRegSeqNo = this.studentClassRepository.findMaxRegSeqNo(studentClassDto.getExamTitle().getId(), studentClassDto.getStudentClass().getAcademicYear().getId(), studentClassDto.getStudentClass().getGrade().getId());
            String regSeq = studentClassDto.getStudentClass().getGrade().getAbbreviate() + "-" + this.getRegSeqNoInMyanmar(String.valueOf(maxRegSeqNo + 1));
            studentClassDto.setRegSeqNo(maxRegSeqNo + 1);
            studentClassDto.setRegNo(regSeq);
            this.studentClassRepository.save(this.studentClassMapper.mapDtoToEntity(studentClassDto));

            for(ExamDto examDto : examDtoList) {
                AttendanceDto attendanceDto = new AttendanceDto();
                attendanceDto.setPresent(false);
                attendanceDto.setExam(examDto);
                attendanceDto.setStudentClass(studentClassDto);
                this.attendanceRepository.save(this.attendanceMapper.mapDtoToEntity(attendanceDto));
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

            Image img = Image.getInstance("src/main/resources/imgs/favicon.png");
            img.scaleAbsolute(150f, 130f);
            img.setAlignment(Image.MIDDLE);
            doc.add(img);

            Paragraph dataParagraph = new Paragraph(
                    "("
                            + studentCard.getExamHoldingTimes()
                            + zawgyiConverter.convert(") အကြိမ်\nရက္ခပူရသာသနာလင်္ကာရစာမေးပွဲ\n")
                            + zawgyiConverter.convert(studentClassDto.getStudentClass().getGrade().getName()) + "\n"
                            + zawgyiConverter.convert("စာမေးပွဲဖြေဆိုခွင့်ကတ်ပြား"), titleFont
            );
            dataParagraph.setAlignment(Element.ALIGN_CENTER);
            doc.add(dataParagraph);

            Phrase phrase1 = new Phrase(zawgyiConverter.convert("နေ့စွဲ"),font);
            PdfContentByte canvas1 = writer.getDirectContent();
            ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase1, 50, 50, 0);
            phrase1 = new Phrase(studentCard.getCardDate());
            canvas1 = writer.getDirectContent();
            ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase1, 150, 50, 0);

            Phrase phrase2 = new Phrase(zawgyiConverter.convert("ပြည်နယ်/တိုင်း"), font);
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

        for(PdfNameZipStreamMapping item : map) {
            byte[] bytes = new byte[item.getStream().size()];
            ByteArrayInputStream fis = new ByteArrayInputStream(item.getStream().toByteArray());
            BufferedInputStream bis = new BufferedInputStream(fis);
            zos.putNextEntry(new ZipEntry(item.getEntryName() + ".pdf"));

            int bytesRead;
            while((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            bis.close();
            fis.close();
        }
        zos.closeEntry();
        baos.flush();
        zos.close();
        baos.close();

        ServletOutputStream sos = response.getOutputStream();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Certificates.zip");
        sos.write(baos.toByteArray());
        sos.flush();
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
