package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    String joinQuery = "select s.* from student s where ";

    List<Student> findAllByNrc(String nrc);

    @Query(value = joinQuery + "((s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "((s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<Student> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

    @Query(value = joinQuery + "(s.region_id=:regionId) and ((s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            countQuery = joinQuery + "(s.region_id=:regionId) and ((s.name like :keyword%) or (s.father_name like :keyword%) or (s.monastery_headmaster like :keyword%) or (s.monastery_name like :keyword%))",
            nativeQuery = true)
    Page<Student> findAllByRegionAndKeyword(@Param("regionId") Long regionId, @Param("keyword") String keyword, Pageable sortedByCreatedTimestamp);

}
