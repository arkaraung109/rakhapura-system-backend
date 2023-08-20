package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.ApplicationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserTableRepository extends JpaRepository<ApplicationUser,Long> {

    Optional<ApplicationUser> findByLoginUserName(String loginUserName);

    String joinQuery = "select u.* from user_table u, role r where u.role_id=r.id and ((u.first_name like :keyword%) or (u.last_name like :keyword%) or (u.user_name like :keyword%))";
    String joinQueryOrderBy = " order by r.name, u.first_name, u.last_name, u.user_name";

    //-----User List-----

    @Query(value = joinQuery + joinQueryOrderBy,
            nativeQuery = true)
    List<ApplicationUser> findAllByKeyword(@Param("keyword") String keyword);

    @Query(value = joinQuery + " and (u.role_id=:roleId)" + joinQueryOrderBy,
            nativeQuery = true)
    List<ApplicationUser> findAllByRoleAndKeyword(@Param("roleId") Long roleId, @Param("keyword") String keyword);

    //-----User Page-----

    @Query(value = joinQuery,
            countQuery = joinQuery,
            nativeQuery = true)
    Page<ApplicationUser> findAllByKeyword(@Param("keyword") String keyword, Pageable sortedById);

    @Query(value = joinQuery + " and (u.role_id=:roleId)",
            countQuery = joinQuery + " and (u.role_id=:roleId)",
            nativeQuery = true)
    Page<ApplicationUser> findAllByRoleAndKeyword(@Param("roleId") Long roleId, @Param("keyword") String keyword, Pageable sortedById);

}
