package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, UUID> {
}
