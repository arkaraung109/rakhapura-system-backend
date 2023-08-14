package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.PublicExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicExamResultRepository extends JpaRepository<PublicExamResult, UUID> {

    @Query(value = "select max(serial_no) from public_exam_result", nativeQuery = true)
    Integer findMaxSerialNo();

    Optional<PublicExamResult> findByStudentClassId(UUID studentClassId);

}
