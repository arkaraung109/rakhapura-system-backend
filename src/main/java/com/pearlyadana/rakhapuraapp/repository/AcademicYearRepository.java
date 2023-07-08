package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.AcademicYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear,Long> {

    List<AcademicYear> findAllByName(String name);

    List<AcademicYear> findAllByAuthorizedStatus(boolean authorizedStatus);

    Page<AcademicYear> findAllByNameStartingWithIgnoreCase(String name, Pageable page);

    @Transactional
    @Modifying
    @Query(value = "update academic_year set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
