package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.request.PublicExamResultDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.ExamService;
import com.pearlyadana.rakhapuraapp.service.PublicExamResultService;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/public-exam-results", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PublicExamResultController {

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private PublicExamResultService publicExamResultService;

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<StudentClassDto>> findEachPageBySearching(@RequestParam int page, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long gradeId, @RequestParam String keyword) {
        return new ResponseEntity<>(this.publicExamResultService.findEachPageBySearching(page, academicYearId, examTitleId, gradeId, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> publish(@RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long gradeId, @RequestParam List<UUID> idList) {
        for(UUID id : idList) {
            PublicExamResultDto publicExamResultDto = new PublicExamResultDto();
            StudentClassDto studentClassDto = new StudentClassDto();
            studentClassDto.setId(id);
            publicExamResultDto.setStudentClass(studentClassDto);

            int maxSerialNo = this.publicExamResultService.findMaxSerialNo();
            publicExamResultDto.setSerialNo(maxSerialNo);

            if(this.publicExamResultService.save(publicExamResultDto) != null) {
                StudentClassDto dto = this.studentClassService.findById(id);
                dto.setPublished(true);
                if(this.studentClassService.update(dto, id) == null) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId);
        for(ExamDto examDto : examDtoList) {
            examDto.setPublished(true);
            if(this.examService.update(examDto, examDto.getId()) == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new objects are created.");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
