package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, UUID> {

    @Query(value = "select sum(mark) from student_exam where attendance_id=?1 group by attendance_id", nativeQuery = true)
    Integer findTotalMark(UUID attendanceId);

    @Query(value = "select min(pass) from student_exam where attendance_id=?1", nativeQuery = true)
    Integer findResult(UUID attendanceId);

    Optional<StudentExam> findFirstByExamSubjectIdAndAttendanceId(Long examSubjectId, UUID attendanceId);

    @Query(value = "select se.* from student_exam se, exam_subject es where se.exam_subject_id=es.id and se.attendance_id=?1 order by es.subject_id asc", nativeQuery = true)
    List<StudentExam> findAllByAttendanceIdOrderBySubjectIdAsc(UUID id);

    @Query(value = "select * from student_exam se where exam_subject_id in (select id from exam_subject where exam_id=?1)", nativeQuery = true)
    List<StudentExam> findAllByExamId(Long id);

}
