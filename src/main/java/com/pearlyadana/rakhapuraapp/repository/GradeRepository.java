package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Grade;
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
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllByName(String name);

    List<Grade> findAllByAuthorizedStatus(boolean authorizedStatus);

    @Query(value = "select * from grade where (name like :keyword%) or (remark like :keyword%) or (abbreviate like :keyword%)",
            countQuery = "select * from grade where (name like :keyword%) or (remark like :keyword%) or (abbreviate like :keyword%)",
            nativeQuery = true)
    Page<Grade> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Transactional
    @Modifying
    @Query(value = "update grade set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
