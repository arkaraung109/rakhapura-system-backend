package com.pearlyadana.rakhapuraapp.service;

import com.itextpdf.text.DocumentException;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentCard;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface StudentCardService {

    PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId);

    void generate(StudentCard studentCard, List<StudentClassDto> studentClassDtoList, List<ExamDto> examDtoList, HttpServletResponse response) throws DocumentException, IOException;

}
