package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Class;
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
public interface ClassRepository extends JpaRepository<Class, Long> {

    String joinQuery = "select c.* from class c where (c.name like :keyword%)";

    @Query(value = "select distinct(name) from class where authorized_status=true", nativeQuery = true)
    List<String> findDistinctAll();

    List<Class> findAllByNameAndAcademicYearIdAndGradeId(String name, Long academicYearId, Long gradeId);

    List<Class> findAllByAuthorizedStatus(boolean authorizedStatus);

    List<Class> findAllByAcademicYearIdAndGradeIdAndAuthorizedStatus(Long academicYearId, Long gradeId, boolean authorizedStatus);

    @Query(value = joinQuery,
            countQuery = joinQuery,
            nativeQuery = true)
    Page<Class> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + " and (c.academic_year_id=:academicYearId)",
            countQuery = joinQuery + " and (c.academic_year_id=:academicYearId)",
            nativeQuery = true)
    Page<Class> findAllByAcademicYearAndKeyword(@Param("academicYearId") Long academicYearId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + " and (c.grade_id=:gradeId)",
            countQuery = joinQuery + " and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<Class> findAllByGradeAndKeyword(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            countQuery = joinQuery + " and (c.academic_year_id=:academicYearId) and (c.grade_id=:gradeId)",
            nativeQuery = true)
    Page<Class> findAllByAcademicYearAndGradeAndKeyword(@Param("academicYearId") Long academicYearId, @Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedById);

    @Transactional
    @Modifying
    @Query(value = "update class set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
