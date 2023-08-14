package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Award;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    List<Award> findAllByAwardAndEventDateAndStudentId(String award, String eventDate, UUID studentId);

    @Query(value = "select * from award a, student s where a.student_id=s.id and ((a.award like :keyword%) or (a.description like :keyword%) or (a.event_date like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))",
            nativeQuery = true)
    List<Award> findAllBySearching(@Param("keyword") String keyword);

    @Query(value = "select * from award a, student s where a.student_id=s.id and ((a.award like :keyword%) or (a.description like :keyword%) or (a.event_date like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))",
            countQuery = "select * from award a, student s where a.student_id=s.id and ((a.award like :keyword%) or (a.description like :keyword%) or (a.event_date like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))",
            nativeQuery = true)
    Page<Award> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

}
