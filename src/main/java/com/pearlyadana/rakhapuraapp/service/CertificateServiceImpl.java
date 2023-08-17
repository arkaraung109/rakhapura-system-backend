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

            Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
            doc.setMargins((float) 0.5, (float) 0.5, (float) 0.5, (float) 0.5);
            PdfWriter writer = PdfWriter.getInstance(doc, pdfStream);
            doc.open();

            Image img = Image.getInstance("src/main/resources/imgs/favicon.png");
            img.scaleAbsolute(150f, 130f);
            img.setAbsolutePosition(224, 700);
            img.setAlignment(Image.MIDDLE);
            doc.add(img);

            Paragraph titleParagraph = new Paragraph(
                    zawgyiConverter.convert(
                    "("
                            + certificate.getExamHoldingTimes()
                            + ") အကြိမ်\nရက္ခပူရသာသနာလင်္ကာရစာမေးပွဲ\n"
                            + studentClass.getStudentClass().getGrade().getName()), titleFont
            );
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setSpacingBefore(150);
            doc.add(titleParagraph);

            Paragraph yearParagraph = new Paragraph(
                    zawgyiConverter.convert(
                            "သာသနာတော်နှစ် ("
                                    + certificate.getTharthanarYear() + ")  ကောဇာသက္ကရာဇ် ("
                                    + certificate.getKawzarYear() + ")  ခရစ်နှစ် ("
                                    + certificate.getChrisYear() + ")"
                    )
                    , titleFont
            );
            yearParagraph.setAlignment(Element.ALIGN_CENTER);
            doc.add(yearParagraph);

            Paragraph dataParagraph = new Paragraph(
                zawgyiConverter.convert(
                    studentClass.getStudent().getMonasteryTownship() + "မြို့နယ်၊ "
                    + studentClass.getStudent().getMonasteryName() + "ကျောင်းတိုက်မှ\n\n"
                    + "ပဓာနနာယကဆရာတော် "
                    + studentClass.getStudent().getMonasteryHeadmaster() + "၏တပည့်\n\n"
                    + studentClass.getStudent().getAddress() + "နေ "
                    + studentClass.getStudent().getFatherName() + " + "
                        + studentClass.getStudent().getMotherName() + " တို့၏သား\n\n"
                        + studentClass.getStudent().getName() + "သည် "
                        + certificate.getExamDate() + "တွင်  ဆင်ယင်ကျင်းပပြုလုပ်ခဲ့သော\n\nရက္ခပူရသာသနာလင်္ကာရစာမေးပွဲတွင် အောင်စဥ္အမှတ်("
                        + publicExamResultDto.getSerialNo() + ") ဖြင့်\nဖြေဆိုအောင်မြင်တော်မူသောကြောင့်\n\nရက္ခပူရသာသနာလင်္ကာရအသင်းကြီးက ဤအောင်လက်မှတ်ကို\nကြည်ညိဳလေးမြတ်စွာ ဆက်ကပ်ပါသည်။"
                )
                , titleFont
            );
            dataParagraph.setAlignment(Element.ALIGN_CENTER);
            dataParagraph.setSpacingBefore(60);
            doc.add(dataParagraph);

            Phrase releasedDateMyanPhrase = new Phrase(zawgyiConverter.convert(this.getReleasedDateInMyanmar(certificate.getReleasedDate())), font);
            PdfContentByte releaseDateMyanCanvas = writer.getDirectContent();
            ColumnText.showTextAligned(releaseDateMyanCanvas, Element.ALIGN_LEFT, releasedDateMyanPhrase, 30, 36, 0);

            Phrase releasedDateEngPhrase = new Phrase(zawgyiConverter.convert(certificate.getReleasedDate()), font);
            PdfContentByte releaseDateEngCanvas = writer.getDirectContent();
            ColumnText.showTextAligned(releaseDateEngCanvas, Element.ALIGN_LEFT, releasedDateEngPhrase, 505, 36, 0);

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
