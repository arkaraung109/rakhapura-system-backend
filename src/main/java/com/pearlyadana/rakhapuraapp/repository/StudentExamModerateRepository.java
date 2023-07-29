package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.StudentExamModerate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentExamModerateRepository extends JpaRepository<StudentExamModerate, UUID> {

    Optional<StudentExamModerate> findFirstByExamSubjectIdAndAttendanceId(Long examSubjectId, UUID attendanceId);

}
