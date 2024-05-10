package com.example.cal_dia_mem.member.dto;

import com.example.cal_dia_mem.member.entity.SiteUserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private long id;
    @Email(message = "이메일의 형식이 옳지 않습니다.")
    private String memberEmail;
    @Size(min=8,max=16 , message = "8자리 이상의 비밀번호를 사용해주세요.")
    private String memberPassword;
    private String memberName;

    private String memberNickname;

    //Entity 를 DTO 로 변환 후 DTO 리턴
    public static MemberDTO tomemberDTO(SiteUserEntity siteUserEntity){
         MemberDTO memberDTO =new MemberDTO();
         memberDTO.setId(siteUserEntity.getId());
         memberDTO.setMemberEmail(siteUserEntity.getMemberEmail());
         memberDTO.setMemberPassword(siteUserEntity.getMemberPassword());
         memberDTO.setMemberName(siteUserEntity.getMemberName());
         memberDTO.setMemberNickname(siteUserEntity.getMemberNickname());
         return memberDTO;
    }
}
