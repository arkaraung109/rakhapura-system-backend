package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.Hostel;
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
public interface HostelRepository extends JpaRepository<Hostel, Long> {

    List<Hostel> findAllByNameAndAddress(String name, String address);

    List<Hostel> findAllByAuthorizedStatus(boolean authorizedStatus);

    @Query(value = "select * from hostel where name like :keyword% or address like :keyword% or phone like :keyword%",
            countQuery = "select * from hostel where name like :keyword% or address like :keyword% or phone like :keyword%",
            nativeQuery = true)
    Page<Hostel> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Transactional
    @Modifying
    @Query(value = "update hostel set authorized_status=true, authorized_user_id=?2 where id=?1", nativeQuery = true)
    void authorizeById(Long id, Long authorizedUserId);

}
