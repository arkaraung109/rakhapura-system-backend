package com.pearlyadana.rakhapuraapp.service;

import com.google.myanmartools.Transliterate;
import com.google.myanmartools.TransliterateU2Z;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.pearlyadana.rakhapuraapp.entity.PublicExamResult;
import com.pearlyadana.rakhapuraapp.mapper.PublicExamResultMapper;
import com.pearlyadana.rakhapuraapp.model.request.Certificate;
import com.pearlyadana.rakhapuraapp.model.request.PdfNameZipStreamMapping;
import com.pearlyadana.rakhapuraapp.model.request.PublicExamResultDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.repository.PublicExamResultRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private PublicExamResultRepository publicExamResultRepository;

    private final PublicExamResultMapper mapper = Mappers.getMapper(PublicExamResultMapper.class);

    private static final Transliterate zawgyiConverter = new TransliterateU2Z("Unicode to Zawgyi");

    @Override
    public void generate(Certificate certificate, List<StudentClassDto> passedStudentList, HttpServletResponse response) throws IOException, DocumentException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            certificate.setExamDate(outputDateFormat.format(inputDateFormat.parse(certificate.getExamDate())));
            certificate.setReleasedDate(outputDateFormat.format(inputDateFormat.parse(certificate.getReleasedDate())));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }

        BaseFont unicode = BaseFont.createFont("src/main/resources/fonts/ZawgyiOne.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(unicode, 12, Font.BOLD);
        Font titleFont = new Font(unicode, 16, Font.BOLD);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        List<PdfNameZipStreamMapping> map = new ArrayList<>();

        for(StudentClassDto studentClass : passedStudentList) {
            Optional<PublicExamResult> optional = this.publicExamResultRepository.findByStudentClassId(studentClass.getId());
            PublicExamResultDto publicExamResultDto = optional.map(this.mapper::mapEntityToDto).get();

            PdfNameZipStreamMapping entry = new PdfNameZipStreamMapping();
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            entry.setEntryName(studentClass.getRegNo());
            entry.setStream(pdfStream);
            map.add(entry);

            Document doc = new Document(PageSize.A4, 36, 36, 90, 36);
            doc.setMargins((float) 0.5, (float) 0.5, (float) 0.5, (float) 0.5);
            PdfWriter writer = PdfWriter.getInstance(doc, pdfStream);
            doc.open();

            Phrase examHoldingTimesPhrase = new Phrase(zawgyiConverter.convert("(" + certificate.getExamHoldingTimes() + ")" + " အကြိမ်"), titleFont);
            PdfContentByte examNameTitleCanvas = writer.getDirectContent();
            ColumnText.showTextAligned(examNameTitleCanvas, Element.ALIGN_LEFT, examHoldingTimesPhrase, 245, 510, 0);

            Phrase tharthanarYearPhrase = new Phrase(zawgyiConverter.convert(certificate.getTharthanarYear()), font);
            PdfContentByte allYearCanvas = writer.getDirectContent();
            ColumnText.showTextAligned(allYearCanvas, Element.ALIGN_LEFT, tharthanarYearPhrase, 65, 470, 0);

            Phrase kawzarYearPhrase = new Phrase(zawgyiConverter.convert(certificate.getKawzarYear()), font);
            ColumnText.showTextAligned(allYearCanvas, Element.ALIGN_LEFT, kawzarYearPhrase, 260, 470, 0);

            Phrase chrisYearPhrase = new Phrase(zawgyiConverter.convert(certificate.getChrisYear()), font);
            ColumnText.showTextAligned(allYearCanvas, Element.ALIGN_LEFT, chrisYearPhrase, 455, 470, 0);

            Phrase monasteryTownshipPhrase = new Phrase(zawgyiConverter.convert(studentClass.getStudent().getMonasteryTownship()), font);
            PdfContentByte line1Canvas = writer.getDirectContent();
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, monasteryTownshipPhrase, 35, 425, 0);

            Phrase text1 = new Phrase(zawgyiConverter.convert("မြို့"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text1, 195, 425, 0);

            Phrase monasteryNamePhrase = new Phrase(zawgyiConverter.convert(studentClass.getStudent().getMonasteryName()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, monasteryNamePhrase, 245, 425, 0);

            Phrase text2 = new Phrase(zawgyiConverter.convert("မှ"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text2, 470, 425, 0);

            Phrase text3 = new Phrase(zawgyiConverter.convert("ပဓာနနာယကဆရာတော်"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text3, 35, 400, 0);

            Phrase monasteryHeadmasterPhrase = new Phrase(zawgyiConverter.convert(studentClass.getStudent().getMonasteryHeadmaster()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, monasteryHeadmasterPhrase, 250, 400, 0);

            Phrase text4 = new Phrase(zawgyiConverter.convert("၏တပည့်"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text4, 505, 400, 0);

            Phrase address = new Phrase(zawgyiConverter.convert(studentClass.getStudent().getAddress()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, address, 35, 375, 0);

            Phrase text5 = new Phrase(zawgyiConverter.convert("နေ"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text5, 275, 375, 0);

            Phrase parentsPhrase = new Phrase(zawgyiConverter.convert(studentClass.getStudent().getFatherName()) + " + " + zawgyiConverter.convert(studentClass.getStudent().getMotherName()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, parentsPhrase, 295, 375, 0);

            Phrase text6 = new Phrase(zawgyiConverter.convert("တို့၏သား"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text6, 505, 375, 0);

            Phrase namePhrase = new Phrase(zawgyiConverter.convert(studentClass.getStudent().getName()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, namePhrase, 35, 350, 0);

            Phrase text7 = new Phrase(zawgyiConverter.convert("သည်"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text7, 160, 350, 0);

            Phrase examDatePhrase = new Phrase(zawgyiConverter.convert(certificate.getExamDate()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, examDatePhrase, 200, 350, 0);

            Phrase text8 = new Phrase(zawgyiConverter.convert("ရက်တို့၌  ဆင်ယင်ကျင်းပပြုလုပ်ခဲ့သော"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text8, 350, 350, 0);

            Phrase text9 = new Phrase(zawgyiConverter.convert("ရက္ခပူရသာသနာလင်္ကာရစာမေးပွဲတွင်"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text9, 35, 325, 0);

            Phrase text10 = new Phrase(zawgyiConverter.convert("အောင်စဉ်အမှတ် ("), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text10, 219, 325, 0);

            Phrase regSeqNoPhrase = new Phrase(String.valueOf(publicExamResultDto.getSerialNo()), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, regSeqNoPhrase, 305, 325, 0);

            Phrase text11 = new Phrase(zawgyiConverter.convert(") ဖြင့် ဖြေဆိုအောင်မြင်တော်မူသောကြောင့်"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text11, 360, 325, 0);

            Phrase text12 = new Phrase(zawgyiConverter.convert("ရက္ခပူရသာသနာလင်္ကာရအသင်းကြီးက ဤအောင်လက်မှတ်ကို ကြည်ညို‌ လေးမြတ်စွာ ဆက်ကပ်ပါသည်။"), font);
            ColumnText.showTextAligned(line1Canvas, Element.ALIGN_LEFT, text12, 35, 300, 0);

            Phrase releasedDateMyanPhrase = new Phrase(zawgyiConverter.convert(this.getReleasedDateInMyanmar(certificate.getReleasedDate())), font);
            PdfContentByte releaseDateMyanCanvas = writer.getDirectContent();
            ColumnText.showTextAligned(releaseDateMyanCanvas, Element.ALIGN_LEFT, releasedDateMyanPhrase, 30, 9, 0);

            Phrase releasedDateEngPhrase = new Phrase(zawgyiConverter.convert(certificate.getReleasedDate()), font);
            PdfContentByte releaseDateEngCanvas = writer.getDirectContent();
            ColumnText.showTextAligned(releaseDateEngCanvas, Element.ALIGN_LEFT, releasedDateEngPhrase, 320, 9, 0);

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
            while ((bytesRead = bis.read(bytes)) != -1) {
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

    private String getReleasedDateInMyanmar(String date) {
        StringBuilder sb = new StringBuilder();
        for(Character chr : date.toCharArray()) {
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
                case '/': sb.append('/'); break;
            }
        }
        return sb.toString();
    }

}
