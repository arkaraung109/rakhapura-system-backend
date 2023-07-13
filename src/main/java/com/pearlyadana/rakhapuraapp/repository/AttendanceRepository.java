package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    String joinQuery = "select a.* from attendance a, exam e, student_class sc, student s where a.exam_id=e.id and a.student_class_id=sc.id and sc.student_id=s.id and a.present=:present and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))";

    Page<Attendance> findAllByPresent(boolean present, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery,
            countQuery = joinQuery,
            nativeQuery = true)
    Page<Attendance> findAllByKeywordAndPresent(@Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.academic_year_id=:academicYearId)",
            countQuery = joinQuery + " and (e.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndKeywordAndPresent(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.exam_title_id=:examTitleId)",
            countQuery = joinQuery + " and (e.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<Attendance> findAllByExamTitleAndKeywordAndPresent(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQuery + " and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllBySubjectTypeAndKeywordAndPresent(@Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId)",
            countQuery = joinQuery + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndExamTitleAndKeywordAndPresent(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQuery + " and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndSubjectTypeAndKeywordAndPresent(@Param("academicYearId") Long academicYearId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQuery + " and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllByExamTitleAndSubjectTypeAndKeywordAndPresent(@Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

    @Query(value = joinQuery + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            countQuery = joinQuery + " and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId)",
            nativeQuery = true)
    Page<Attendance> findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeywordAndPresent(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, @Param("present") boolean present, Pageable sortedById);

}
