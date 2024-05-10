package com.example.cal_dia_mem.member.entity;

import com.example.cal_dia_mem.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table (name="siteUser_table")
public class SiteUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String memberName;

    @Column
    private String memberPassword;

    @Column(unique = true)
    private String memberEmail;

    @Column(unique = true)
    private String memberNickname;


    // DTO --> Entity로 변환 후 Entity 리턴
    public static SiteUserEntity toSiteUserEntity(MemberDTO memberDTO){
        SiteUserEntity siteUserEntity = new SiteUserEntity();
        siteUserEntity.setMemberEmail(memberDTO.getMemberEmail());
        siteUserEntity.setMemberName(memberDTO.getMemberName());
        siteUserEntity.setMemberPassword(memberDTO.getMemberPassword());
        siteUserEntity.setMemberNickname(memberDTO.getMemberNickname());
        return siteUserEntity;
    }
}
