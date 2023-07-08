package com.pearlyadana.rakhapuraapp.repository;

import com.pearlyadana.rakhapuraapp.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserTableRepository extends JpaRepository<ApplicationUser,Long> {

    Optional<ApplicationUser> findByLoginUserName(String loginUserName);

}
