package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.SubjectType;
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
public interface SubjectTypeRepository extends JpaRepository<SubjectType, Long> {

    String joinQuery = "select s.* from subject_type s where (s.name like :keyword%)";

    List<SubjectType> findAllByNameAndGradeId(String name, Long gradeId);

    List<SubjectType> findAllByAuthorizedStatus(boolean authorizedStatus);

    List<SubjectType> findAllByGradeIdAndAuthorizedStatus(Long gradeId, boolean authorizedStatus);

    @Query(value = joinQuery,
            countQuery = joinQuery,
            nativeQuery = true)
    Page<SubjectType> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + " and (s.grade_id=:gradeId)",
            countQuery = joinQuery + " and (s.grade_id=:gradeId)",
            nativeQuery = true)
    Page<SubjectType> findAllByGradeAndKeyword(@Param("gradeId") Long gradeId, @Param("keyword") String keyword, Pageable sortedById);

    @Transactional
    @Modifying
    @Query(value = "update subject_type set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
