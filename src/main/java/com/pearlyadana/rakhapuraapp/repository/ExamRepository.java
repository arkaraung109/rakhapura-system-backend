package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    String joinQuery = "select e.* from exam e where ";

    Optional<Exam> findByAcademicYearIdAndExamTitleIdAndSubjectTypeId(Long academicYearId, Long examTitleId, Long subjectTypeId);

    List<Exam> findAllByAcademicYearIdAndExamTitleIdAndAuthorizedStatus(Long academicYearId, Long examTitleId, boolean authorizedStatus);

    List<Exam> findAllByAcademicYearIdAndExamTitleIdAndSubjectTypeId(Long academicYearId, Long examTitleId, Long subjectTypeId);

    @Query(value = joinQuery + "((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.academic_year_id=:academicYearId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.academic_year_id=:academicYearId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByAcademicYearAndKeyword(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.exam_title_id=:examTitleId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.exam_title_id=:examTitleId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByExamTitleAndKeyword(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllBySubjectTypeAndKeyword(@Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByAcademicYearAndExamTitleAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByAcademicYearAndSubjectTypeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByExamTitleAndSubjectTypeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "(e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            countQuery = joinQuery + "(e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((e.exam_date like :keyword%) or (e.time like :keyword%) or (e.pass_mark like :keyword%) or (e.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<Exam> findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Transactional
    @Modifying
    @Query(value = "update exam set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
