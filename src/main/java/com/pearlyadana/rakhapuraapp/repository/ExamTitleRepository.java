package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.ExamTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExamTitleRepository extends JpaRepository<ExamTitle, Long> {

    List<ExamTitle> findAllByName(String name);

    List<ExamTitle> findAllByAuthorizedStatus(boolean authorizedStatus);

    Page<ExamTitle> findAllByNameStartingWithIgnoreCase(String name, Pageable page);

    @Transactional
    @Modifying
    @Query(value = "update exam_title set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
