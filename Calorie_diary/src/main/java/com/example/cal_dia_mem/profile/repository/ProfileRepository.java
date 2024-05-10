package com.example.cal_dia_mem.profile.repository;

import com.example.cal_dia_mem.profile.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity,String> {

    Optional<ProfileEntity> findByMemberEmail(String memberEmail);
}

