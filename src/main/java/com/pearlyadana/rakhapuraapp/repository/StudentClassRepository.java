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

    String joinQueryStudentClass = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))";
    String joinQueryArrival = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id and sc.arrival=:arrival and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))";
    String joinQueryStudentCard = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id and sc.reg_no=null and sc.reg_seq_no=0 and ((s.name like :keyword%) or (s.father_name like :keyword%))";

    @Query(value = "select max(sc.reg_seq_no) from student_class sc, class c where sc.class_id=c.id and sc.exam_title_id=:examTitleId and c.academic_year_id=:academicYearId and c.grade_id=:gradeId", nativeQuery = true)
    int findMaxRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId);

    List<StudentClass> findAllByStudentId(UUID id);

    @Query(value = "select sc.* from student_class sc, class c where sc.class_id=c.id and sc.exam_title_id=:examTitleId and c.academic_year_id=:academicYearId and sc.student_id=:studentId", nativeQuery = true)
    List<StudentClass> findAllByExamTitleIdAndAcademicYearIdAndStudentId(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentId") UUID studentId);

    Page<StudentClass> findAllByArrival(boolean arrival, Pageable sortedByCreatedTimestamp);

    Page<StudentClass> findAllByArrivalAndRegNoAndRegSeqNo(boolean arrival, String regNo, int regSeqNo, Pageable sortedByCreatedTimestamp);

    //-----Student Class-----

    @Query(value = joinQueryStudentClass,
            countQuery = joinQueryStudentClass,
            nativeQuery = true)
    Page<StudentClass> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndKeyword(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndKeyword(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentClass + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndKeyword(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByClassAndKeyword(@Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndClassAndKeyword(@Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndClassAndKeyword(@Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndClassAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    //-----Arrival-----

    @Query(value = joinQueryArrival,
            countQuery = joinQueryArrival,
            nativeQuery = true)
    Page<StudentClass> findAllByKeywordAndArrival(@Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryArrival + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.grade_id=:gradeId)",
            countQuery = joinQueryArrival + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndKeywordAndArrival(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByClassAndKeywordAndArrival(@Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndClassAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndClassAndKeywordAndArrival(@Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndClassAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival, Pageable sortedByCreatedTimestamp);

    //-----Student Card-----

    @Query(value = joinQueryStudentCard,
            countQuery = joinQueryStudentCard,
            nativeQuery = true)
    Page<StudentClass> findAllByKeywordAndRegNoAndRegSeqNo(@Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndKeywordAndRegNoAndRegSeqNo(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndKeywordAndRegNoAndRegSeqNo(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByClassAndKeywordAndRegNoAndRegSeqNo(@Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndKeywordAndRegNoAndRegSeqNo(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeywordAndRegNoAndRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

}
