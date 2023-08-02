package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    String joinQueryNotPresent = "select a.* from attendance a, exam e, student_class sc, student s where a.exam_id=e.id and a.student_class_id=sc.id and sc.student_id=s.id and a.present=false and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))";
    String joinQueryPresent = "select a.* from attendance a, exam e, student_class sc, student s, class c where a.exam_id=e.id and a.student_class_id=sc.id and sc.student_id=s.id and sc.class_id=c.id and a.present=true and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))";
    String joinQueryPresentForModeration = "select a.* from attendance a, student_exam_moderate sem, exam e, student_class sc, student s, class c where a.id=sem.attendance_id and a.exam_id=e.id and a.student_class_id=sc.id and sc.student_id=s.id and sc.class_id=c.id and a.present=true and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))";
    String groupByQuery = " group by a.student_class_id";

    List<Attendance> findByOrderByCreatedTimestampAsc();

    @Query(value = "select a.* from attendance a, exam e where a.exam_id=e.id and a.student_class_id=?1 order by e.subject_type_id asc", nativeQuery = true)
    List<Attendance> findAllByStudentClassIdOrderBySubjectTypeIdAsc(UUID id);

    @Query(value = "select a.* from attendance a, exam e where a.exam_id=e.id and a.student_class_id=?1 and a.present=true order by e.subject_type_id asc", nativeQuery = true)
    List<Attendance> findAllByStudentClassIdAndPresentOrderBySubjectTypeIdAsc(UUID id);

    //-----Not Present-----

    @Query(value = joinQueryNotPresent,
            countQuery = joinQueryNotPresent,
            nativeQuery = true)
    Page<Attendance> findAllByKeywordAndNotPresent(@Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId)",
            countQuery = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndKeywordAndNotPresent(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.exam_title_id=:examTitleId)",
            countQuery = joinQueryNotPresent + " and (e.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<Attendance> findAllByExamTitleAndKeywordAndNotPresent(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQueryNotPresent + " and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllBySubjectTypeAndKeywordAndNotPresent(@Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId)",
            countQuery = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndExamTitleAndKeywordAndNotPresent(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndSubjectTypeAndKeywordAndNotPresent(@Param("academicYearId") Long academicYearId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQueryNotPresent + " and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllByExamTitleAndSubjectTypeAndKeywordAndNotPresent(@Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQueryNotPresent + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeywordAndNotPresent(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    //-----Student Exam-----
    @Query(value = joinQueryPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + groupByQuery + " order by sc.reg_no",
            nativeQuery = true)
    List<Attendance> findAllByAcademicYearAndExamTitleAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + groupByQuery + " order by sc.reg_no",
            countQuery = joinQueryPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + groupByQuery + " order by sc.reg_no",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndExamTitleAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable pageable);

    //-----Student Exam Moderation-----
    @Query(value = joinQueryPresentForModeration + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + groupByQuery + " order by sc.reg_no",
            nativeQuery = true)
    List<Attendance> findAllModerationByAcademicYearAndExamTitleAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryPresentForModeration + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + groupByQuery + " order by sc.reg_no",
            countQuery = joinQueryPresentForModeration + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + groupByQuery + " order by sc.reg_no",
            nativeQuery = true)
    Page<Attendance> findAllModerationByAcademicYearAndExamTitleAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable pageable);

}
