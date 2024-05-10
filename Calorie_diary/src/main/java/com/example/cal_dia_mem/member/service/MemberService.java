package com.example.cal_dia_mem.member.service;

import com.example.cal_dia_mem.member.dto.MemberDTO;
import com.example.cal_dia_mem.profile.entity.ProfileEntity;
import com.example.cal_dia_mem.member.entity.SiteUserEntity;
import com.example.cal_dia_mem.member.repository.MemberRepository;
import com.example.cal_dia_mem.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // dto -> entity로 변환
        // repository의 save메서드 호출
        SiteUserEntity siteUserEntity = SiteUserEntity.toSiteUserEntity(memberDTO);
        memberRepository.save(siteUserEntity);
    }

    public static Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 email로 DB에서 조회
        // 2. DB에서 조회한 정보가 회원이 입력한 정보와 일치하는지 판단

        Optional<SiteUserEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있음
            SiteUserEntity siteUserEntity =byMemberEmail.get();
            if(siteUserEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                //비밀번호가 일치
                //entity객체->dto객체로 변환 후 리턴
                MemberDTO dto =MemberDTO.tomemberDTO(siteUserEntity);
                return dto;
            }
            else{
                //비밀번호가 불일치
                return null;
            }
        }
        else{
            // 조회 결과가 없음
            return null;
        }
    }

    public String emailCheck(String memberEmail) {
        Optional<SiteUserEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){
            //조회결과가 있다 -> 사용할 수 없다.
            return "no";
        } else{
            //조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    public String NicknameCheck(String memberNickname) {
        Optional<SiteUserEntity> byMemberNickname = memberRepository.findByMemberNickname(memberNickname);
        if(byMemberNickname.isPresent()){
            return "no";
        } else{
            return "ok";
        }
    }
}
