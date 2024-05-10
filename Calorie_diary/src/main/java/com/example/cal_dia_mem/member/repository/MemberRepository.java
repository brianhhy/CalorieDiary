package com.example.cal_dia_mem.member.repository;

import com.example.cal_dia_mem.member.entity.SiteUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<SiteUserEntity,Long> {
    //이메일로 회원 정보 조회 (SELECT * FROM siteuser_table where memberEmail)
    Optional<SiteUserEntity>findByMemberEmail(String memberEmail);
    Optional<SiteUserEntity>findByMemberNickname(String memberNickname);
}

