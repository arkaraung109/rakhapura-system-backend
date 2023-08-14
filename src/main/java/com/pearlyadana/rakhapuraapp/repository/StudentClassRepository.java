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
    String joinQueryStudentCard = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id and sc.reg_no is null and sc.reg_seq_no=0 and sc.arrival=true and sc.hostel_id is not null";
    String joinQueryHostelNotPresent = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id and sc.reg_no is null and sc.reg_seq_no=0 and sc.arrival=true and sc.hostel_id is null and ((s.name like :keyword%) or (s.father_name like :keyword%))";
    String joinQueryHostelPresent = "select sc.* from student_class sc, class c, student s where sc.class_id=c.id and sc.student_id=s.id and sc.hostel_id is not null and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))";
    String joinQueryOrderBy = " order by c.academic_year_id, sc.exam_title_id, c.grade_id, sc.reg_no";

    @Query(value = "select max(sc.reg_seq_no) from student_class sc, class c where sc.class_id=c.id and sc.exam_title_id=:examTitleId and c.academic_year_id=:academicYearId and c.grade_id=:gradeId", nativeQuery = true)
    Integer findMaxRegSeqNo(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId);

    List<StudentClass> findAllByStudentId(UUID id);

    @Query(value = "select sc.* from student_class sc, class c where sc.class_id=c.id and sc.exam_title_id=:examTitleId and c.academic_year_id=:academicYearId and sc.student_id=:studentId", nativeQuery = true)
    List<StudentClass> findAllByExamTitleIdAndAcademicYearIdAndStudentId(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentId") UUID studentId);

    @Query(value = "select sc.* from student_class sc, class c, public_exam_result per where sc.class_id=c.id and sc.id=per.student_class_id and c.academic_year_id=:academicYearId and sc.exam_title_id=:examTitleId and c.grade_id=:gradeId order by sc.reg_no", nativeQuery = true)
    List<StudentClass> findPassedAllByAcademicYearAndExamTitleAndGrade(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId);

    @Query(value = "select sc.* from student_class sc, class c, student s, public_exam_result per where sc.class_id=c.id and sc.student_id=s.id and sc.id=per.student_class_id and c.academic_year_id=:academicYearId and sc.exam_title_id=:examTitleId and c.grade_id=:gradeId and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%)) order by sc.reg_no",
            countQuery = "select sc.* from student_class sc, class c, student s, public_exam_result per where sc.class_id=c.id and sc.student_id=s.id and sc.id=per.student_class_id and c.academic_year_id=:academicYearId and sc.exam_title_id=:examTitleId and c.grade_id=:gradeId and ((sc.reg_no like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%)) order by sc.reg_no",
            nativeQuery = true)
    Page<StudentClass> findPassedAllByAcademicYearAndExamTitleAndGrade(@Param("academicYearId") Long academicYearId, @Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable pageable);

    //-----Student Class List-----

    @Query(value = joinQueryStudentClass + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByKeyword(@Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndKeyword(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndKeyword(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByGradeAndKeyword(@Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByClassAndKeyword(@Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndGradeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndClassAndKeyword(@Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByGradeAndClassAndKeyword(@Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndGradeAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndGradeAndClassAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    @Query(value = joinQueryStudentClass + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeyword(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword);

    //-----Student Class Page-----

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

    //-----Arrival List-----

    @Query(value = joinQueryArrival + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByKeywordAndArrival(@Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByGradeAndKeywordAndArrival(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByClassAndKeywordAndArrival(@Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndGradeAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndGradeAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndClassAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByGradeAndClassAndKeywordAndArrival(@Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndGradeAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndGradeAndClassAndKeywordAndArrival(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    @Query(value = joinQueryArrival + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (c.name=:studentClass)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeywordAndArrival(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("studentClass") String studentClass, @Param("keyword") String keyword, @Param("arrival") boolean arrival);

    //-----Arrival Page-----

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

    //-----Student Card Page-----

    @Query(value = joinQueryStudentCard,
            countQuery = joinQueryStudentCard,
            nativeQuery = true)
    Page<StudentClass> findAllForStudentCard(Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleForStudentCard(@Param("examTitleId") Long examTitleId, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearForStudentCard(@Param("academicYearId") Long academicYearId, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeForStudentCard(@Param("gradeId") Long gradeId, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearForStudentCard(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeForStudentCard(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeForStudentCard(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryStudentCard + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeForStudentCard(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, Pageable sortedByCreatedTimestamp);

    //-----Hostel Not Present Page-----

    @Query(value = joinQueryHostelNotPresent,
            countQuery = joinQueryHostelNotPresent,
            nativeQuery = true)
    Page<StudentClass> findAllByKeywordAndHostelNotPresent(@Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId)",
            countQuery = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndKeywordAndHostelNotPresent(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryHostelNotPresent + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndKeywordAndHostelNotPresent(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelNotPresent + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndKeywordAndHostelNotPresent(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndKeywordAndHostelNotPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndKeywordAndHostelNotPresent(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelNotPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndKeywordAndHostelNotPresent(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelNotPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndHostelNotPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    //-----Hostel Present List-----

    @Query(value = joinQueryHostelPresent + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByKeywordAndHostelPresent(@Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByGradeAndKeywordAndHostelPresent(@Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByHostelAndKeywordAndHostelPresent(@Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndGradeAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndGradeAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndHostelAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByGradeAndHostelAndKeywordAndHostelPresent(@Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndGradeAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByAcademicYearAndGradeAndHostelAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword);

    //-----Hostel Present Page-----

    @Query(value = joinQueryHostelPresent,
            countQuery = joinQueryHostelPresent,
            nativeQuery = true)
    Page<StudentClass> findAllByKeywordAndHostelPresent(@Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelPresent + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndKeywordAndHostelPresent(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByHostelAndKeywordAndHostelPresent(@Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndHostelAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByGradeAndHostelAndKeywordAndHostelPresent(@Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndGradeAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByAcademicYearAndGradeAndHostelAndKeywordAndHostelPresent(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            countQuery = joinQueryHostelPresent + " and (sc.exam_title_id=:examTitleId) and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId) and (sc.hostel_id=:hostelId)",
            nativeQuery = true)
    Page<StudentClass> findAllByExamTitleAndAcademicYearAndGradeAndHostelAndKeywordAndHostelPresent(@Param("examTitleId") Long examTitleId, @Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("hostelId") Long hostelId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

}
