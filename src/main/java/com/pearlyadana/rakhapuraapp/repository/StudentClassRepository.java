package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.StudentClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, UUID> {

    String joinQuery = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id ";

    List<StudentClass> findAllByStudentId(UUID id);

    @Query(value = "select sc.* from student_class sc, class c where sc.class_id=c.id and sc.exam_title_id=:examTitleId and c.academic_year_id=:academicYearId and sc.student_id=:studentId", nativeQuery = true)
    List<StudentClass> findAllByExamTitleIdAndAcademicYearIdAndStudentId(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentId") UUID studentId);

    Page<StudentClass> findAllByArrival(boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndKeyword(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.academic_year_id=:academicYearId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.academic_year_id=:academicYearId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndKeyword(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndKeyword(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByClassAndKeyword(@Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.academic_year_id=:academicYearId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.academic_year_id=:academicYearId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndClassAndKeyword(@Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndClassAndKeyword(@Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndClassAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass) and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

}
