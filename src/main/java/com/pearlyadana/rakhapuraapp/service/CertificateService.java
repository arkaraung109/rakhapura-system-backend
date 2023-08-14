package com.pearlyadana.rakhapuraapp.service;

import com.itextpdf.text.DocumentException;
import com.pearlyadana.rakhapuraapp.model.request.Certificate;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface CertificateService {

    void generate(Certificate certificate, List<StudentClassDto> passedStudentList, HttpServletResponse response) throws IOException, DocumentException;

}
