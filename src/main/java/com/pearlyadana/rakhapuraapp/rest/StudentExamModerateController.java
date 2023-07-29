package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamModerateDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.service.StudentExamModerateService;
import com.pearlyadana.rakhapuraapp.service.StudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/student-exam-moderates", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentExamModerateController {

    @Autowired
    private StudentExamService studentExamService;

    @Autowired
    private StudentExamModerateService studentExamModerateService;

    @GetMapping("/filterOne")
    public ResponseEntity<StudentExamModerateDto> findByExamSubjectAndAttendance(@RequestParam Long examSubjectId, @RequestParam UUID attendanceId) {
        return new ResponseEntity<>(this.studentExamModerateService.findByExamSubjectAndAttendance(examSubjectId, attendanceId), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_MARK_ENTRY)
    public ResponseEntity<CustomHttpResponse> moderate(@RequestBody StudentExamDto body) {
        StudentExamDto dto = this.studentExamService.findById(body.getId());
        int passMark = dto.getExamSubject().getPassMark();
        StudentExamModerateDto studentExamModerateDto = new StudentExamModerateDto();
        studentExamModerateDto.setMark(dto.getMark());
        studentExamModerateDto.setExamSubject(dto.getExamSubject());
        studentExamModerateDto.setAttendance(dto.getAttendance());
        dto.setMark(passMark);
        dto.setPass(true);
        StudentExamModerateDto sem = this.studentExamModerateService.findByExamSubjectAndAttendance(dto.getExamSubject().getId(), dto.getAttendance().getId());
        if(sem != null) {
            studentExamModerateDto.setId(sem.getId());
        }
        if(this.studentExamModerateService.save(studentExamModerateDto) != null) {
            if(this.studentExamService.save(dto) != null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
