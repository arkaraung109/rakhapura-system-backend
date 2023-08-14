package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Punishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PunishmentRepository extends JpaRepository<Punishment, Long> {

    List<Punishment> findAllByPunishmentAndEventDateAndStudentId(String punishment, String eventDate, UUID studentId);

    @Query(value = "select * from punishment p, student s where p.student_id=s.id and ((p.punishment like :keyword%) or (p.description like :keyword%) or (p.event_date like :keyword%) or (p.start_date like :keyword%) or (p.end_date like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))",
            nativeQuery = true)
    List<Punishment> findAllBySearching(@Param("keyword") String keyword);

    @Query(value = "select * from punishment p, student s where p.student_id=s.id and ((p.punishment like :keyword%) or (p.description like :keyword%) or (p.event_date like :keyword%) or (p.start_date like :keyword%) or (p.end_date like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))",
            countQuery = "select * from punishment p, student s where p.student_id=s.id and ((p.punishment like :keyword%) or (p.description like :keyword%) or (p.event_date like :keyword%) or (p.start_date like :keyword%) or (p.end_date like :keyword%) or (s.name like :keyword%) or (s.father_name like :keyword%))",
            nativeQuery = true)
    Page<Punishment> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

}
