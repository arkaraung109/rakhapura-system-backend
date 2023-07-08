package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.ExamSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {

    String joinQuery = "select es.* from exam_subject es, exam e where es.exam_id=e.id ";

    List<ExamSubject> findAllByExamId(Long examId);

    List<ExamSubject> findAllByExamIdAndSubjectId(Long examId, Long subjectId);

    List<ExamSubject> findAllByAuthorizedStatus(boolean authorizedStatus);

    @Query(value = joinQuery + "and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndKeyword(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.exam_title_id=:examTitleId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.exam_title_id=:examTitleId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByExamTitleAndKeyword(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllBySubjectTypeAndKeyword(@Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllBySubjectAndKeyword(@Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndExamTitleAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndSubjectTypeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndSubjectAndKeyword(@Param("academicYearId") Long academicYearId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByExamTitleAndSubjectTypeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.exam_title_id=:examTitleId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.exam_title_id=:examTitleId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByExamTitleAndSubjectAndKeyword(@Param("examTitleId") Long examTitleId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllBySubjectTypeAndSubjectAndKeyword(@Param("subjectTypeId") Long subjectTypeId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndExamTitleAndSubjectAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndSubjectTypeAndSubjectAndKeyword(@Param("academicYearId") Long academicYearId, @Param("subjectTypeId") Long subjectTypeId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByExamTitleAndSubjectTypeAndSubjectAndKeyword(@Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            countQuery = joinQuery + "and (e.academic_year_id=:academicYearId) and (e.exam_title_id=:examTitleId) and (e.subject_type_id=:subjectTypeId) and (es.subject_id=:subjectId) and ((es.pass_mark like :keyword%) or (es.mark_percentage like :keyword%))",
            nativeQuery = true)
    Page<ExamSubject> findAllByAcademicYearAndExamTitleAndSubjectTypeAndSubjectAndKeyword(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("subjectTypeId") Long subjectTypeId, @Param("subjectId") Long subjectId, @Param("keyword") String keyword, Pageable sortedById);

    @Transactional
    @Modifying
    @Query(value = "update exam_subject set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
