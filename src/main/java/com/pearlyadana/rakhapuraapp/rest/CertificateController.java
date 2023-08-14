package com.pearlyadana.rakhapuraapp.rest;

import com.itextpdf.text.DocumentException;
import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.Certificate;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.service.CertificateService;
import com.pearlyadana.rakhapuraapp.service.PublicExamResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CertificateController {

    @Autowired
    private PublicExamResultService publicExamResultService;

    @Autowired
    private CertificateService certificateService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void generate(@RequestBody Certificate body, HttpServletResponse response) throws DocumentException, IOException {
        List<StudentClassDto> passedStudentList = this.publicExamResultService.findPassedAllByAcademicYearAndExamTitleAndGrade(body.getAcademicYearId(), body.getExamTitleId(), body.getGradeId());
        this.certificateService.generate(body, passedStudentList, response);
    }

}
